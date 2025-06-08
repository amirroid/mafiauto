package ir.amirroid.mafiauto.game.engine.actions.schedule

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.actions.role.RoleAction

@Immutable
data class ScheduledAction(
    val action: RoleAction,
    val executeOnDay: Int
)