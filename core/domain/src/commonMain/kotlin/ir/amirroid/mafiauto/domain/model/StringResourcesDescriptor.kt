package ir.amirroid.mafiauto.domain.model

import org.jetbrains.compose.resources.StringResource

data class StringResourcesDescriptor(
    val resource: StringResource,
    val formatArgs: List<String> = emptyList()
)