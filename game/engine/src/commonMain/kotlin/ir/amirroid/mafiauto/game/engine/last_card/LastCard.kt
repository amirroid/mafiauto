package ir.amirroid.mafiauto.game.engine.last_card

import org.jetbrains.compose.resources.StringResource

interface LastCard {
    val name: StringResource
    val explanation: StringResource
    val key: String
}