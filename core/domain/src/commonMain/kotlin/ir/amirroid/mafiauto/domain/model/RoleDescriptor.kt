package ir.amirroid.mafiauto.domain.model

import org.jetbrains.compose.resources.StringResource

data class RoleDescriptor(
    val name: StringResource,
    val explanation: StringResource,
    val alignment: Alignment,
    val key: String,
    val isOptionalAbility: Boolean,
    val nightActionRequiredPicks: Int,
    val instantAction: InstantAction?,
    val maxAbilityUses: Int,
)

enum class Alignment { Mafia, Civilian, Neutral }