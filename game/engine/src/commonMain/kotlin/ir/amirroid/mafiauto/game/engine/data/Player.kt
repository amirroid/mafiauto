package ir.amirroid.mafiauto.game.engine.data

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.game.engine.role.Role

@Immutable
data class Player(
    val id: Long,
    val name: String,
    val role: Role,
    val isAlive: Boolean = true,
    val isKick: Boolean = false
)


fun List<Player>.findWithId(id: Long) = find { it.id == id }