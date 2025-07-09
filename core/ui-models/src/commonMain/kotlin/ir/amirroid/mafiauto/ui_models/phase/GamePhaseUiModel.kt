package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.night_target_otpions.NightTargetOptionsUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.night_action_result.NightActionsResultUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.StringResource

sealed class GamePhaseUiModel(val displayName: StringResource) {
    data object Day : GamePhaseUiModel(Resources.strings.day)
    data object Voting : GamePhaseUiModel(Resources.strings.voting)
    data object FinalDebate : GamePhaseUiModel(Resources.strings.day)
    data class Defending(val defenders: ImmutableList<PlayerWithRoleUiModel>) :
        GamePhaseUiModel(Resources.strings.voting)

    data class LastCard(val player: PlayerWithRoleUiModel) :
        GamePhaseUiModel(Resources.strings.voting)

    data class Fate(
        val targetPlayer: PlayerWithRoleUiModel,
        val sameVotesDefenders: ImmutableList<PlayerWithRoleUiModel>
    ) : GamePhaseUiModel(Resources.strings.voting)

    data class Night(val options: ImmutableList<NightTargetOptionsUiModel>) :
        GamePhaseUiModel(Resources.strings.night)

    data class Result(val result: NightActionsResultUiModel) :
        GamePhaseUiModel(Resources.strings.nightActionsResult)

    data class End(
        val winnerAlignment: Alignment
    ) : GamePhaseUiModel(Resources.strings.day)
}