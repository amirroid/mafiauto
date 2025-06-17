package ir.amirroid.mafiauto.game.engine.models

import org.jetbrains.compose.resources.StringResource

data class StringResourcesMessage(
    val resource: StringResource,
    val formatArgs: List<String> = emptyList()
)