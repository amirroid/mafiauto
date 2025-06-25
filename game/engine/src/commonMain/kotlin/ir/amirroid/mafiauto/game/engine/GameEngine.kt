package ir.amirroid.mafiauto.game.engine

import ir.amirroid.mafiauto.game.engine.actions.role.NightActionHandler
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.base.PhaseUpdater
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.last_card.LastCardHandler
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.NightActionsResult
import ir.amirroid.mafiauto.game.engine.models.NightTargetOptions
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.models.findWithId
import ir.amirroid.mafiauto.game.engine.provider.last_card.LastCardsProvider
import ir.amirroid.mafiauto.game.engine.role.Alignment
import ir.amirroid.mafiauto.game.engine.role.GodFather
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
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
) : ActionsHandler, PlayerTransformer, LastCardHandler, PhaseUpdater {
    private val _currentDay = MutableStateFlow(initialDay)
    private val _currentPhase = MutableStateFlow(initialPhase)
    private val _players = MutableStateFlow(emptyList<Player>())
    private val _scheduledActions = MutableStateFlow(emptyList<ScheduledAction>())
    private val _lastCards = MutableStateFlow(emptyList<LastCard>())
    private val _statusCheckCount = MutableStateFlow(0)
    private val _playerTurn = MutableStateFlow(0)
    private val _messages = Channel<StringResource>(capacity = Channel.BUFFERED)

    val currentDay: StateFlow<Int> = _currentDay
    val currentPhase: StateFlow<Phase> = _currentPhase
    val players: StateFlow<List<Player>> = _players
    val lastCards: StateFlow<List<LastCard>> = _lastCards
    val statusCheckCount: StateFlow<Int> = _statusCheckCount
    val playerTurn: StateFlow<Int> = _playerTurn
    val messages: Flow<StringResource> = _messages.receiveAsFlow()

    private val nightActionsHistory = mutableMapOf<Int, List<NightAction>>()

    override fun sendMessage(message: StringResource) {
        _messages.trySend(message)
    }

    override fun updatePhase(newPhase: Phase) {
        if (currentPhase.value is Phase.End) return
        _currentPhase.update { newPhase }
    }

    fun reset() {
        _currentDay.value = initialDay
        _currentPhase.value = initialPhase
        _scheduledActions.value = emptyList()
        _statusCheckCount.value = 0
        _playerTurn.value = 0
        _lastCards.value = lastCardsProvider.getAllLastCards().shuffled()
        nightActionsHistory.clear()
    }

    fun proceedToNextPhase() {
        when (_currentPhase.value) {
            is Phase.Day -> {
                unSilentPlayers()
                updatePhase(Phase.Voting)
            }

            is Phase.Defending, is Phase.Voting -> {
                proceedToNightPhase()
            }

            is Phase.Result -> {
                val winnerAlignment = getWinnerAlignment()
                if (winnerAlignment == null) {
                    proceedToDayPhase()
                } else updatePhase(Phase.End(winnerAlignment))
            }

            else -> Unit
        }
    }


    private fun proceedToDayPhase() {
        incrementDay()
        if (players.value.count { it.isInGame } > 3) {
            updatePhase(Phase.Day)
        } else {
            processedToFinalDebatePhase()
        }
    }

    private fun processedToFinalDebatePhase() {
        val winner = players.value.firstOrNull { it.role.winsIfFinalDebate }
        if (winner == null) {
            updatePhase(Phase.FinalDebate)
        } else {
            updatePhase(Phase.End(winnerAlignment = winner.role.alignment))
        }
    }

    private fun updateCurrentTurn() {
        val allPlayers = players.value
        val currentTurnIndex = playerTurn.value
        val currentPlayerTurn = allPlayers.getOrNull(currentTurnIndex) ?: return

        val filteredPlayers = allPlayers.filter { it.isInGame || it.id == currentPlayerTurn.id }
        if (filteredPlayers.isEmpty()) return

        val currentIndexInFiltered = filteredPlayers.indexOfFirst { it.id == currentPlayerTurn.id }
        val nextPlayer =
            filteredPlayers[(currentIndexInFiltered + GameInfo.SPEAKERS_PER_TURN) % filteredPlayers.size]

        val newPlayerTurnIndex = allPlayers.indexOfFirst { it.id == nextPlayer.id }
        if (newPlayerTurnIndex != -1) {
            _playerTurn.update { newPlayerTurnIndex }
        }
    }

    private fun getWinnerAlignment(): Alignment? {
        val allPlayers = players.value

        allPlayers.firstOrNull { it.role.hasWon(allPlayers) == true }
            ?.let { return it.role.alignment }

        val mafiaCount = allPlayers.count { it.role.alignment == Alignment.Mafia && it.isAlive }
        val nonMafiaCount = allPlayers.count { it.role.alignment != Alignment.Mafia && it.isAlive }

        return when {
            mafiaCount == 0 -> Alignment.Civilian
            mafiaCount >= nonMafiaCount -> Alignment.Mafia
            else -> null
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
        updatePlayers(updatePlayer(players.value, targetId, transform))
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
        val currentNight = currentDay.value

        val godfatherReplacement = allPlayers
            .filter { it.role.alignment == Alignment.Mafia && it.isInGame }
            .minByOrNull { it.role.executionOrder }
            ?.takeIf {
                allPlayers.none { p -> p.role.key == RoleKeys.GOD_FATHER && p.isInGame }
            }?.copy(role = GodFather)

        val playersForNight = if (godfatherReplacement != null)
            allPlayers + godfatherReplacement else allPlayers

        val options = playersForNight
            .filter { it.role.hasNightAction(allPlayers) && it.role.targetNightToWakingUp?.let { night -> night == currentNight } != false && it.isInGame }
            .map { player ->
                val previewsTargets = nightActionsHistory[currentNight - 1]
                    ?.find { it.player.id == player.id }?.targets

                NightTargetOptions(
                    player = player,
                    availableTargets = player.role.getNightActionTargetPlayers(
                        previewsTargets,
                        allPlayers
                    ),
                    message = player.role.getNightActionMessage(allPlayers),
                    isReplacement = player.id == godfatherReplacement?.id
                )
            }
            .filter { it.message != null || it.availableTargets.isNotEmpty() }
            .sortedBy { it.player.role.executionOrder }

        updatePhase(Phase.Night(options))
    }

    fun applyLastCard(card: LastCard, pickedPlayers: List<Player>) {
        _lastCards.update { previewsList -> previewsList.filter { it.key != card.key } }
        val phase = currentPhase.value
        if (phase !is Phase.LastCard) return
        val targetPlayer = phase.player
        card.applyAction(
            player = targetPlayer,
            pickedPlayers = pickedPlayers,
            allPlayers = players.value,
            handler = this
        )
        proceedToNightPhase()
    }

    fun handleNightActions(actions: List<NightAction>) {
        nightActionsHistory[_currentDay.value] = actions
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

        val nightActionHandler = createActionHandler { newPlayers ->
            currentPlayers = newPlayers
        }

        applyNightActions(inDayActions, { currentPlayers }, nightActionHandler)
        currentPlayers = currentPlayers.map { it.copy(canUseAbility = true) }
        updatePlayers(currentPlayers)

        val result = getNightActionsResult(initialPlayers, currentPlayers)
        updateCurrentTurn()
        _currentPhase.update { Phase.Result(result) }
    }

    private fun getActionsForCurrentDay(): List<ScheduledAction> {
        return _scheduledActions.value.filter { it.executeOnDay == _currentDay.value }
    }

    private fun createActionHandler(onPlayersUpdated: (List<Player>) -> Unit): ActionsHandler {
        return object : ActionsHandler {
            override fun sendMessage(message: StringResource) {
                this@GameEngine.sendMessage(message)
            }

            override fun updatePlayers(newPlayers: List<Player>) {
                onPlayersUpdated(newPlayers)
            }

            override fun updatePhase(newPhase: Phase) {
                updatePhase(newPhase)
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
                scheduledAction.action.let {
                    it.replacementRole?.getNightAction() ?: it.player.role.getNightAction()
                } ?: return@forEach

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


    fun handleFinalTrustVotes(
        trustVotes: Map<Player, Player>
    ) {
        val trustCountByPlayer = trustVotes.values.groupingBy { it }.eachCount()
        val playerWithMajorityTrust = trustCountByPlayer.entries.find { it.value >= 2 }?.key

        if (playerWithMajorityTrust != null) {
            if (playerWithMajorityTrust.role.alignment == Alignment.Mafia) {
                updatePhase(Phase.End(winnerAlignment = Alignment.Mafia))
            } else {
                updatePhase(Phase.End(winnerAlignment = Alignment.Civilian))
            }
        } else {
            updatePhase(Phase.Voting)
        }
    }


    override fun updatePlayers(newPlayers: List<Player>) {
        var currentPlayers = newPlayers
        val actionHandler = createActionHandler {
            currentPlayers = it
        }
        val previewsPlayers = players.value
        val killedPlayers =
            newPlayers.filter { !it.isAlive && previewsPlayers.findWithId(it.id)!!.isAlive }
        killedPlayers.forEach { it.role.onKillPlayer(currentPlayers, actionHandler) }

        _players.update { currentPlayers }
    }
}