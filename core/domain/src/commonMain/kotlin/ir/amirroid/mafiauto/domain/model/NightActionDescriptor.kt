package ir.amirroid.mafiauto.domain.model

data class NightActionDescriptor(
    val player: PlayerWithRole,
    val targets: List<PlayerWithRole>,
    val replacementRole: RoleDescriptor? = null
)