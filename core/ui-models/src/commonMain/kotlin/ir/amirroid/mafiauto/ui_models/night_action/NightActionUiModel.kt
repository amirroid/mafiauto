package ir.amirroid.mafiauto.ui_models.night_action

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class NightActionUiModel(
    val player: PlayerWithRoleUiModel,
    val targets: ImmutableList<PlayerWithRoleUiModel>,
    val replacementRole: RoleUiModel? = null
)