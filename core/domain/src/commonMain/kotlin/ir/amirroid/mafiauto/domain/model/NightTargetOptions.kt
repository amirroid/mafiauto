package ir.amirroid.mafiauto.domain.model

data class NightTargetOptions(
    val player: PlayerWithRole,
    val availableTargets: List<PlayerWithRole>
)
