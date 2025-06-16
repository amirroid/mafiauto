package ir.amirroid.mafiauto.game.engine.base

import org.jetbrains.compose.resources.StringResource

interface MessageHandler {
    fun sendMessage(message: StringResource)
}