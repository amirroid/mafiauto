package ir.amirroid.mafiauto.domain.model.player

import ir.amirroid.mafiauto.domain.model.effect.PlayerEffectDomain

data class Player(
    val id: Long,
    val name: String,
    val isAlive: Boolean = true,
    val isKick: Boolean = false,
    val isSilenced: Boolean = false,
    val canUseAbility: Boolean = true,
    val remainingAbilityUses: Int = Int.MAX_VALUE,
    val effects: List<PlayerEffectDomain> = emptyList()
) {
    val isInGame = isAlive && !isKick
}