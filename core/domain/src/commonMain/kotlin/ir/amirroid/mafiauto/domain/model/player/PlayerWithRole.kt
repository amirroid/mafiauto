package ir.amirroid.mafiauto.domain.model.player

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor

@Immutable
data class PlayerWithRole(
    val player: Player,
    val role: RoleDescriptor
)