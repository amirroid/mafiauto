package ir.amirroid.mafiauto.game.engine.data

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ir.amirroid.mafiauto.game.engine.role.Role

@Stable
data class Player(
    val id: Int,
    val name: String,
    val role: Role,
    val isVisibleToMafia: Boolean
) {
    var isAlive by mutableStateOf(true)
}