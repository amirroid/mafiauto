package ir.amirroid.mafiauto.domain.model

import org.jetbrains.compose.resources.StringResource

data class LastCardDescriptor(
    val name: StringResource,
    val key: String,
    val targetCount: Int
)