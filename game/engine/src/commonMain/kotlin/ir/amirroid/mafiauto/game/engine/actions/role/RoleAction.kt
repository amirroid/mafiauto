package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player

interface NightActionHandler : ActionsHandler

interface RoleAction : PlayerTransformer {
    val delayInDays: Int get() = 0
    fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    )
}