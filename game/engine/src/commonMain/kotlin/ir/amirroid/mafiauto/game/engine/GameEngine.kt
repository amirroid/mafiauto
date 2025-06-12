package ir.amirroid.mafiauto.game.engine

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.actions.GameActions
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.models.Phase
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.last_card.LastCardsProvider
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

    fun proceedToNextPhase() {
        _currentPhase.update { phase ->
            when (phase) {
                is Phase.Day -> Phase.Voting
                is Phase.Defending -> Phase.Night
                is Phase.Voting -> Phase.Night
                is Phase.Night -> {
                    incrementDay()
                    Phase.Day
                }

                is Phase.Result -> Phase.Day
                else -> phase
            }
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
            topCandidates.size == 1 -> {
                val selectedPlayer = topCandidates.first()
                proceedToLastCardPhase(selectedPlayer)
            }

            topCandidates.size > 1 -> {
                val randomPlayer = topCandidates.random()
                _currentPhase.update { Phase.Fate(targetPlayer = randomPlayer) }
            }

            validVotes.size == 1 -> {
                val threshold = (voteMap.size - 1) / 2
                val entry = validVotes.entries.first()
                if (entry.value >= threshold) {
                    proceedToLastCardPhase(entry.key)
                } else {
                    _currentPhase.update { Phase.Night }
                }
            }

            else -> {
                _currentPhase.update { Phase.Night }
            }
        }
    }

    private fun proceedToLastCardPhase(player: Player) {
        _currentPhase.update { Phase.LastCard(player) }
    }

    private fun goToLastCard(player: Player) {
        _currentPhase.update { Phase.Fate(targetPlayer = player) }
    }

    override fun updatePlayers(newPlayers: List<Player>) {
        _players.update { newPlayers }
    }
}