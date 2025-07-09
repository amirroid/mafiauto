package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.ui_models.night_target_otpions.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import ir.amirroid.mafiauto.ui_models.night_action_result.toUiModel
import kotlinx.collections.immutable.toImmutableList

fun GamePhase.toUiModel() = when (this) {
    is GamePhase.Day -> GamePhaseUiModel.Day
    is GamePhase.FinalDebate -> GamePhaseUiModel.FinalDebate
    is GamePhase.Night -> GamePhaseUiModel.Night(options = options.map { it.toUiModel() }
        .toImmutableList())

    is GamePhase.Voting -> GamePhaseUiModel.Voting
    is GamePhase.Result -> GamePhaseUiModel.Result(result = result.toUiModel())
    is GamePhase.Fate -> GamePhaseUiModel.Fate(
        targetPlayer = targetPlayer.toUiModel(),
        sameVotesDefenders = sameVotesDefenders.map { it.toUiModel() }.toImmutableList()
    )

    is GamePhase.LastCard -> GamePhaseUiModel.LastCard(player = player.toUiModel())
    is GamePhase.Defending -> GamePhaseUiModel.Defending(
        defenders = defenders.map { it.toUiModel() }.toImmutableList()
    )

    is GamePhase.End -> GamePhaseUiModel.End(
        winnerAlignment = winnerAlignment,
    )
}