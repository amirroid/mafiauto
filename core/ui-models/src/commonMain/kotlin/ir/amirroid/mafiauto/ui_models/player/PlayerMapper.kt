package ir.amirroid.mafiauto.ui_models.player

import ir.amirroid.mafiauto.domain.model.player.Player
import ir.amirroid.mafiauto.ui_models.effect.toUiModel
import kotlinx.collections.immutable.toImmutableList

fun Player.toUiModel() = PlayerUiModel(
    name = name,
    id = id,
    isAlive = isAlive,
    isKick = isKick,
    isSilenced = isSilenced,
    canUseAbility = canUseAbility,
    remainingAbilityUses = remainingAbilityUses,
    effects = effects.map { it.toUiModel() }.toImmutableList()
)