package ir.amirroid.mafiauto.game.engine.models

import ir.amirroid.mafiauto.game.engine.effect.PlayerEffect
import ir.amirroid.mafiauto.game.engine.role.Role

data class Player(
    val id: Long,
    val name: String,
    val role: Role,
    val isAlive: Boolean = true,
    val isKick: Boolean = false,
    val canBackWithSave: Boolean = true,
    val currentHealthPoints: Int = role.healthPoints,
    val isSilenced: Boolean = false,
    val canUseAbility: Boolean = true,
    val remainingAbilityUses: Int = role.maxAbilityUses,
    val effects: List<PlayerEffect> = emptyList()
) {
    val isInGame = isAlive && !isKick
}


fun List<Player>.findWithId(id: Long) = find { it.id == id }
fun List<Player>.findWithRoleKey(role: String) = find { it.role.key == role }