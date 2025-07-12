package ir.amirroid.mafiauto.ui_models.player_with_role

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.ui_models.player.toUiModel
import ir.amirroid.mafiauto.ui_models.role.toUiModel

fun PlayerWithRole.toUiModel(): PlayerWithRoleUiModel {
    return PlayerWithRoleUiModel(
        player = player.toUiModel(),
        role = role.toUiModel()
    )
}