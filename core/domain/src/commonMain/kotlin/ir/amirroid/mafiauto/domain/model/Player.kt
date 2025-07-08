package ir.amirroid.mafiauto.domain.model

data class Player(
    val id: Long,
    val name: String,
    val isAlive: Boolean = true,
    val isKick: Boolean = false,
    val isSilenced: Boolean = false,
    val canUseAbility: Boolean = true,
    val remainingAbilityUses: Int = Int.MAX_VALUE,
) {
    val isInGame = isAlive && !isKick
}