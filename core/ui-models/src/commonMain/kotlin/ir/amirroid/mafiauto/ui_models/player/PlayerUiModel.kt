package ir.amirroid.mafiauto.ui_models.player

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.effect.PlayerEffectUiModel
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class PlayerUiModel(
    val name: String,
    val id: Long,
    val isKick: Boolean,
    val isAlive: Boolean,
    val isSilenced: Boolean,
    val canUseAbility: Boolean,
    val remainingAbilityUses: Int,
    val effects: ImmutableList<PlayerEffectUiModel>
) {
    val isInGame = isAlive && !isKick
    val hasLimitToUseAbilities = remainingAbilityUses != Int.MAX_VALUE
}