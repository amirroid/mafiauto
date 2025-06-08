package ir.amirroid.mafiauto.game.engine.state

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import ir.amirroid.mafiauto.game.engine.actions.GameActions
import ir.amirroid.mafiauto.game.engine.actions.schedule.ScheduledAction
import ir.amirroid.mafiauto.game.engine.data.Player


enum class Phase {
    Night, Day, Voting, Result
}

@Stable
class GameState(
    players: List<Player>,
    initialPhase: Phase = Phase.Day,
    initialDay: Int = 0
) : GameActions() {
    var day by mutableIntStateOf(initialDay)
        private set

    var phase by mutableStateOf(initialPhase)
        private set

    override val players = players.toMutableStateList()
        private set

    val pendingActions = mutableStateListOf<ScheduledAction>()
        private set

    fun nextPhase() {
        phase = when (phase) {
            Phase.Day -> Phase.Voting
            Phase.Voting -> Phase.Night
            Phase.Night -> {
                day += 1
                Phase.Day
            }

            Phase.Result -> Phase.Day
        }
    }

    fun reset() {
        day = 0
        phase = Phase.Day
        pendingActions.clear()
    }
}