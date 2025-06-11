package ir.amirroid.mafiauto.game.engine

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.actions.GameActions
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.data.Player
import ir.amirroid.mafiauto.game.engine.utils.PlayersHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


enum class Phase {
    Night, Day, Voting, Result
}

@Immutable
class GameEngine(
    private val initialPhase: Phase = Phase.Day,
    private val initialDay: Int = 0
) : GameActions(), PlayersHolder {

    private val _currentDay = MutableStateFlow(initialDay)
    private val _currentPhase = MutableStateFlow(initialPhase)
    override val _players = MutableStateFlow(emptyList<Player>())
    private val _scheduledActions = MutableStateFlow(emptyList<ScheduledAction>())
    private val _statusCheckCount = MutableStateFlow(0)

    val currentDay: StateFlow<Int> = _currentDay
    val currentPhase: StateFlow<Phase> = _currentPhase
    val scheduledActions: StateFlow<List<ScheduledAction>> = _scheduledActions
    val statusCheckCount: StateFlow<Int> = _statusCheckCount

    fun proceedToNextPhase() {
        _currentPhase.update { phase ->
            when (phase) {
                Phase.Day -> Phase.Voting
                Phase.Voting -> Phase.Night
                Phase.Night -> {
                    incrementDay()
                    Phase.Day
                }

                Phase.Result -> Phase.Day
            }
        }
    }

    private fun incrementDay() {
        _currentDay.update { it + 1 }
    }

    fun reset() {
        _currentDay.value = initialDay
        _currentPhase.value = initialPhase
        _scheduledActions.value = emptyList()
        _statusCheckCount.value = 0
    }

    fun incrementStatusCheckCount() {
        _statusCheckCount.update { it + 1 }
    }

    fun decreaseStatusCheckCount() {
        _statusCheckCount.update { it.minus(1).coerceAtLeast(0) }
    }
}