package ir.amirroid.mafiauto.game.engine.last_card

import ir.amirroid.mafiauto.game.engine.base.MessageHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.base.PlayersUpdater
import ir.amirroid.mafiauto.game.engine.models.Player
import org.jetbrains.compose.resources.StringResource

interface LastCardHandler : PlayersUpdater, MessageHandler

interface LastCard : PlayerTransformer {
    val name: StringResource
    val explanation: StringResource
    val key: String
    val targetCount: Int

    fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handler: LastCardHandler
    )
}