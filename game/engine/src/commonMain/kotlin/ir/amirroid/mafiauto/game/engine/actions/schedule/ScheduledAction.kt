package ir.amirroid.mafiauto.game.engine.actions.schedule

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.actions.role.RoleAction
import ir.amirroid.mafiauto.game.engine.models.NightAction

@Immutable
data class ScheduledAction(
    val action: NightAction,
    val executeOnDay: Int?
)