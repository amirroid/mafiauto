package ir.amirroid.mafiauto.domain.model

data class NightActionDescriptor(
    val player: PlayerWithRole,
    val target: PlayerWithRole,
)