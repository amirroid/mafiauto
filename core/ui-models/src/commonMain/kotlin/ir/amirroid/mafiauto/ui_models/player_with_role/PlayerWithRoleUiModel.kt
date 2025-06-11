package ir.amirroid.mafiauto.ui_models.player_with_role

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import org.jetbrains.compose.resources.StringResource

@Immutable
data class PlayerWithRoleUiModel(
    val player: PlayerUiModel,
    val role: RoleUiModel
)