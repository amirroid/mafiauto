package ir.amirroid.mafiauto.ui_models.night_action

import ir.amirroid.mafiauto.domain.model.game.NightActionDescriptor
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import ir.amirroid.mafiauto.ui_models.role.toUiModel
import kotlinx.collections.immutable.toImmutableList

fun NightActionDescriptor.toUiModel() = NightActionUiModel(
    player = player.toUiModel(),
    targets = targets.map { it.toUiModel() }.toImmutableList(),
    replacementRole = replacementRole?.toUiModel()
)