package ir.amirroid.mafiauto.game.engine.models

data class NightTargetOptions(
    val player: Player,
    val availableTargets: List<Player>,
    val message: StringResourcesMessage?,
    val isReplacement: Boolean
)