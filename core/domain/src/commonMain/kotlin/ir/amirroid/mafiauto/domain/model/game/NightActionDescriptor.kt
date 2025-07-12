package ir.amirroid.mafiauto.domain.model.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor

data class NightActionDescriptor(
    val player: PlayerWithRole,
    val targets: List<PlayerWithRole>,
    val replacementRole: RoleDescriptor? = null
)