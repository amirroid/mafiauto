package ir.amirroid.mafiauto.ui_models.night_target_otpions

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel

@Immutable
data class NightTargetOptionsUiModel(
    val player: PlayerWithRoleUiModel,
    val availableTargets: List<PlayerWithRoleUiModel>
)