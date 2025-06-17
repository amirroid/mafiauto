package ir.amirroid.mafiauto.game.engine

import ir.amirroid.mafiauto.game.engine.actions.role.NightActionHandler
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.base.MessageHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.last_card.LastCardHandler
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.NightActionsResult
import ir.amirroid.mafiauto.game.engine.models.NightTargetOptions
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.last_card.LastCardsProvider
import ir.amirroid.mafiauto.game.engine.base.PlayersUpdater
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.StringResource


class GameEngine(
    private val lastCardsProvider: LastCardsProvider,
    private val initialPhase: Phase = Phase.Day,
    private val initialDay: Int = 0,
) : PlayersUpdater, MessageHandler, PlayerTransformer, LastCardHandler {
    private val _currentDay = MutableStateFlow(initialDay)
    private val _currentPhase = MutableStateFlow(initialPhase)
    private val _players = MutableStateFlow(emptyList<Player>())
    private val _scheduledActions = MutableStateFlow(emptyList<ScheduledAction>())
    private val _lastCards = MutableStateFlow(emptyList<LastCard>())
    private val _statusCheckCount = MutableStateFlow(0)
    private val _messages = Channel<StringResource>(capacity = Channel.BUFFERED)

    val currentDay: StateFlow<Int> = _currentDay
    val currentPhase: StateFlow<Phase> = _currentPhase
    val players: StateFlow<List<Player>> = _players
    val scheduledActions: StateFlow<List<ScheduledAction>> = _scheduledActions
    val lastCards: StateFlow<List<LastCard>> = _lastCards
    val statusCheckCount: StateFlow<Int> = _statusCheckCount
    val messages: Flow<StringResource> = _messages.receiveAsFlow()

    private val nightActionsHistory = mutableMapOf<Int, List<NightAction>>()

    override fun sendMessage(message: StringResource) {
        _messages.trySend(message)
    }

    private fun updatePhase(newPhase: Phase) = _currentPhase.update { newPhase }

    fun proceedToNextPhase() {
        when (_currentPhase.value) {
            is Phase.Day -> {
                unSilentPlayers()
                updatePhase(Phase.Voting)
            }

            is Phase.Defending, is Phase.Voting -> {
                proceedToNightPhase()
            }

            is Phase.Result -> updatePhase(Phase.Day)
            else -> Unit
        }
    }

    private fun unSilentPlayers() {
        updatePlayers(_players.value.map { it.copy(isSilenced = false) })
    }

    fun proceedToDefendingPhase(defenders: List<Player>) {
        _currentPhase.update { Phase.Defending(defenders) }
    }

    private fun incrementDay() {
        _currentDay.update { it + 1 }
    }

    fun reset() {
        _currentDay.value = initialDay
        _currentPhase.value = initialPhase
        _scheduledActions.value = emptyList()
        _statusCheckCount.value = 0
        _lastCards.value = lastCardsProvider.getAllLastCards().shuffled()
        nightActionsHistory.clear()
    }

    fun incrementStatusCheckCount() {
        _statusCheckCount.update { it + 1 }
    }

    fun decreaseStatusCheckCount() {
        _statusCheckCount.update { it.minus(1).coerceAtLeast(0) }
    }

    fun kickPlayer(playerId: Long) {
        updatePlayer(playerId) { copy(isKick = true) }
    }

    fun unKickPlayer(playerId: Long) {
        updatePlayer(playerId) { copy(isKick = false) }
    }

    private fun updatePlayer(
        targetId: Long,
        transform: Player.() -> Player
    ) {
        _players.update {
            updatePlayer(it, targetId, transform)
        }
    }

    fun getDefenseCandidates(
        playerVotes: Map<Player, Int>
    ): List<Player> {
        val threshold = (playerVotes.size - 1) / 2
        return playerVotes
            .filter { it.value >= threshold }
            .keys
            .toList()
    }

    fun handleDefenseVoteResult(voteMap: Map<Player, Int>) {
        val validVotes = voteMap.filterValues { it > 0 }
        val maxVoteCount = validVotes.maxOfOrNull { it.value } ?: 0
        val topCandidates = validVotes.filterValues { it == maxVoteCount }.keys

        when {
            topCandidates.size > 1 -> {
                val randomPlayer = topCandidates.random()
                _currentPhase.update {
                    Phase.Fate(
                        targetPlayer = randomPlayer,
                        sameVotesDefenders = topCandidates.toList()
                    )
                }
            }

            validVotes.isNotEmpty() -> {
                val threshold = (voteMap.size - 1) / 2
                val entry = validVotes.filterValues { it >= threshold }.keys.firstOrNull()
                if (entry != null) {
                    proceedToLastCardPhase(entry)
                } else proceedToNightPhase()
            }

            else -> proceedToNightPhase()
        }
    }

    fun handleFatePhase() {
        proceedToLastCardPhase((_currentPhase.value as Phase.Fate).targetPlayer)
    }

    private fun proceedToLastCardPhase(player: Player) {
        _currentPhase.update { Phase.LastCard(player) }
    }

    private fun proceedToNightPhase() {
        val allPlayers = _players.value
        val options = allPlayers.filter { it.role.hasNightAction }.map { player ->
            val previewsTarget = nightActionsHistory[_currentDay.value - 1]
                ?.find { it.player.id == player.id }?.target
            NightTargetOptions(
                player = player,
                availableTargets = player.role.getNightActionTargetPlayers(
                    previewsTarget,
                    allPlayers
                ),
                message = player.role.getNightActionMessage(allPlayers)
            )
        }.filter { it.message != null || it.availableTargets.isNotEmpty() }
            .sortedBy { it.player.role.executionOrder }
        _currentPhase.update { Phase.Night(options) }
    }

    fun applyLastCard(card: LastCard, pickedPlayers: List<Player>) {
        val phase = currentPhase.value
        if (phase !is Phase.LastCard) return
        val targetPlayer = phase.player
        card.applyAction(
            player = targetPlayer,
            pickedPlayers = pickedPlayers,
            allPlayers = _players.value,
            handler = this
        )
        proceedToNightPhase()
    }

    fun handleNightActions(actions: List<NightAction>) {
        nightActionsHistory[_currentDay.value] = actions
        incrementDay()
        val newScheduledActions =
            actions.sortedBy { it.player.role.submitExecutionOrder }.map { action ->
                ScheduledAction(
                    action,
                    executeOnDay = action.player.role.getNightAction()?.delayInDays?.let { _currentDay.value + it }
                )
            }
        _scheduledActions.update { it + newScheduledActions }
        proceedToResultPhase()
    }

    private fun proceedToResultPhase() {
        val inDayActions = getActionsForCurrentDay()
        val initialPlayers = _players.value
        var currentPlayers = initialPlayers

        val nightActionHandler = createNightActionHandler { newPlayers ->
            currentPlayers = newPlayers
        }

        applyNightActions(inDayActions, { currentPlayers }, nightActionHandler)
        currentPlayers = currentPlayers.map { it.copy(canUseAbility = true) }
        updatePlayers(currentPlayers)

        val result = getNightActionsResult(initialPlayers, currentPlayers)
        _currentPhase.update { Phase.Result(result) }
    }

    private fun getActionsForCurrentDay(): List<ScheduledAction> {
        return _scheduledActions.value.filter { it.executeOnDay == _currentDay.value }
    }

    private fun createNightActionHandler(onPlayersUpdated: (List<Player>) -> Unit): NightActionHandler {
        return object : NightActionHandler {
            override fun sendMessage(message: StringResource) {
                sendMessage(message)
            }

            override fun updatePlayers(newPlayers: List<Player>) {
                onPlayersUpdated(newPlayers)
            }
        }
    }

    private fun applyNightActions(
        actions: List<ScheduledAction>,
        players: () -> List<Player>,
        handler: NightActionHandler
    ) {
        actions.forEach { scheduledAction ->
            val playerRoleAction =
                scheduledAction.action.player.role.getNightAction() ?: return@forEach

            playerRoleAction.apply(
                nightAction = scheduledAction.action,
                players = players.invoke(),
                handler = handler
            )
        }
    }

    private fun getNightActionsResult(
        initialPlayers: List<Player>,
        newPlayers: List<Player>
    ): NightActionsResult {
        return NightActionsResult(
            deathCount = (initialPlayers.count { it.isAlive } - newPlayers.count { it.isAlive }).coerceAtLeast(
                0
            ),
            revivedCount = (newPlayers.count { it.isAlive } - initialPlayers.count { it.isAlive }).coerceAtLeast(
                0
            )
        )
    }

    override fun updatePlayers(newPlayers: List<Player>) {
        _players.update { newPlayers }
    }
}