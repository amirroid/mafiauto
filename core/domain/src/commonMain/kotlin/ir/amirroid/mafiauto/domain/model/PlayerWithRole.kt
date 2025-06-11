package ir.amirroid.mafiauto.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class PlayerWithRole(
    val player: Player,
    val role: RoleDescriptor
)