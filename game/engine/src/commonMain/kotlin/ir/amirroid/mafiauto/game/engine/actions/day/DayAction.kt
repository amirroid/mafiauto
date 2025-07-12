package ir.amirroid.mafiauto.game.engine.actions.day

import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.models.Player

typealias DayActionHandler = ActionsHandler

interface DayAction : PlayerTransformer {
    fun applyAction(
        player: Player,
        selectedPlayers: List<Player>,
        allPlayers: List<Player>,
        handler: DayActionHandler
    )
}