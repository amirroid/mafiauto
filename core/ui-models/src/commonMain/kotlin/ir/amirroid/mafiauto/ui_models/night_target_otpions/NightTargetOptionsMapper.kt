package ir.amirroid.mafiauto.ui_models.night_target_otpions

import ir.amirroid.mafiauto.domain.model.NightTargetOptions
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel

fun NightTargetOptions.toUiModel() = NightTargetOptionsUiModel(
    player = player.toUiModel(),
    availableTargets = availableTargets.map { it.toUiModel() }
)