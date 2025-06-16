package ir.amirroid.mafiauto.ui_models.player

import androidx.compose.runtime.Immutable

@Immutable
data class PlayerUiModel(
    val name: String,
    val id: Long,
    val isKick: Boolean,
    val isAlive: Boolean,
    val isSilenced: Boolean,
    val canUseAbility: Boolean
) {
    val isInGame = isAlive && !isKick
}