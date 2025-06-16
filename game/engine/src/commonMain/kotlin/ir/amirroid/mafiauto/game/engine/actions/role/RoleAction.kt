package ir.amirroid.mafiauto.game.engine.actions.role

import ir.amirroid.mafiauto.game.engine.base.MessageHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.base.PlayersUpdater
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player

interface NightActionHandler : MessageHandler, PlayersUpdater

interface RoleAction : PlayerTransformer {
    val delayInDays: Int get() = 0
    fun apply(
        nightAction: NightAction,
        players: List<Player>,
        handler: NightActionHandler
    )
}