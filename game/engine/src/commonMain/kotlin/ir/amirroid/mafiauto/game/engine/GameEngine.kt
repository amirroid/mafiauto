package ir.amirroid.mafiauto.game.engine

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.actions.GameActions
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.NightTargetOptions
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.last_card.LastCardsProvider
import ir.amirroid.mafiauto.game.engine.role.Alignment
import ir.amirroid.mafiauto.game.engine.utils.PlayersHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


@Immutable
class GameEngine(
    private val lastCardsProvider: LastCardsProvider,
    private val initialPhase: Phase = Phase.Day,
    private val initialDay: Int = 0,
) : GameActions(), PlayersHolder {

    private val _currentDay = MutableStateFlow(initialDay)
    private val _currentPhase = MutableStateFlow(initialPhase)
    private val _players = MutableStateFlow(emptyList<Player>())
    private val _scheduledActions = MutableStateFlow(emptyList<ScheduledAction>())
    private val _lastCards = MutableStateFlow(emptyList<LastCard>())
    private val _statusCheckCount = MutableStateFlow(0)

    val currentDay: StateFlow<Int> = _currentDay
    val currentPhase: StateFlow<Phase> = _currentPhase
    val players: StateFlow<List<Player>> = _players
    val scheduledActions: StateFlow<List<ScheduledAction>> = _scheduledActions
    val lastCards: StateFlow<List<LastCard>> = _lastCards
    val statusCheckCount: StateFlow<Int> = _statusCheckCount

    private fun updatePhase(newPhase: Phase) = _currentPhase.update { newPhase }

    fun proceedToNextPhase() {
        when (_currentPhase.value) {
            is Phase.Day -> updatePhase(Phase.Voting)
            is Phase.Defending, is Phase.Voting -> {
                proceedToNightPhase()
            }

            is Phase.Result -> updatePhase(Phase.Day)
            else -> Unit
        }
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
            it.toMutableList().apply {
                val index = indexOfFirst { player -> player.id == targetId }
                if (index != -1) {
                    this[index] = this[index].transform()
                }
            }
        }
    }


    private fun updatePlayer(
        target: Player,
        transform: Player.() -> Player
    ) {
        _players.update {
            it.toMutableList().apply {
                val index = indexOfFirst { player -> player.id == target.id }
                if (index != -1) {
                    this[index] = this[index].transform()
                }
            }
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
        val options = allPlayers.filter { it.role.hasNightAction }.map {
            NightTargetOptions(it, it.role.getNightActionTargetPlayers(null, allPlayers))
        }.sortedBy { it.player.role.executionOrder }
        _currentPhase.update { Phase.Night(options) }
    }

    fun applyLastCard(card: LastCard, pickedPlayers: List<Player>) {
        val phase = currentPhase.value
        if (phase !is Phase.LastCard) return
        val targetPlayer = phase.player
        card.applyAction(targetPlayer, pickedPlayers)
        proceedToNightPhase()
    }

    fun handleNightActions(actions: List<NightAction>) {
        incrementDay()
        val newScheduledActions = actions.map { action ->
            ScheduledAction(
                action,
                executeOnDay = action.player.role.getNightAction()?.delayInDays?.let { _currentDay.value + it }
            )
        }
        _scheduledActions.update { it + newScheduledActions }
        proceedToResultPhase()
    }

    private fun proceedToResultPhase() {
        val inDayActions = _scheduledActions.value.filter { it.executeOnDay == _currentDay.value }
        var currentPlayers = _players.value
        inDayActions.forEach { scheduledAction ->
            val playerRoleAction =
                scheduledAction.action.player.role.getNightAction() ?: return@forEach

            playerRoleAction.apply(scheduledAction.action, players = currentPlayers) { newPlayers ->
                newPlayers?.let { currentPlayers = it }
            }
        }
        updatePlayers(currentPlayers)
        _currentPhase.update { Phase.Result }
    }

    override fun updatePlayers(newPlayers: List<Player>) {
        _players.update { newPlayers }
    }
}