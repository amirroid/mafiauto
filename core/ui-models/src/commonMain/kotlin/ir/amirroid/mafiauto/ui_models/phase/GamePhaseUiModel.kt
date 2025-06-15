package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.night_target_otpions.NightTargetOptionsUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.StringResource

sealed class GamePhaseUiModel(val displayName: StringResource) {
    data object Day : GamePhaseUiModel(Resources.strings.day)
    data object Voting : GamePhaseUiModel(Resources.strings.voting)
    data class Defending(val defenders: List<PlayerWithRoleUiModel>) :
        GamePhaseUiModel(Resources.strings.voting)

    data class LastCard(val player: PlayerWithRoleUiModel) :
        GamePhaseUiModel(Resources.strings.voting)

    data class Fate(
        val targetPlayer: PlayerWithRoleUiModel,
        val sameVotesDefenders: List<PlayerWithRoleUiModel>
    ) : GamePhaseUiModel(Resources.strings.voting)

    data class Night(val options: List<NightTargetOptionsUiModel>) :
        GamePhaseUiModel(Resources.strings.night)

    data object Result : GamePhaseUiModel(Resources.strings.day)
}