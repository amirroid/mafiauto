package ir.amirroid.mafiauto.game.engine.last_card

import ir.amirroid.mafiauto.game.engine.models.Player
import org.jetbrains.compose.resources.StringResource

interface LastCard {
    val name: StringResource
    val explanation: StringResource
    val key: String
    val targetCount: Int

    fun applyAction(player: Player, pickedPlayers: List<Player>) {}
}