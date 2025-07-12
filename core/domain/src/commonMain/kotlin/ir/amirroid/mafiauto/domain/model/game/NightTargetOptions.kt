package ir.amirroid.mafiauto.domain.model.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.model.string.StringResourcesDescriptor

data class NightTargetOptions(
    val player: PlayerWithRole,
    val availableTargets: List<PlayerWithRole>,
    val message: StringResourcesDescriptor?,
    val isReplacement: Boolean
)
