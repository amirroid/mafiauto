package ir.amirroid.mafiauto.domain.model

import org.jetbrains.compose.resources.StringResource

data class RoleDescriptor(
    val name: StringResource,
    val explanation: StringResource,
    val alignment: Alignment,
    val key: String,
    val hasNightAction: Boolean,
    val isOptionalAbility: Boolean,
    val nightActionRequiredPicks: Int,
    val instantAction: InstantAction?,
)

enum class Alignment { Mafia, Civilian, Neutral }