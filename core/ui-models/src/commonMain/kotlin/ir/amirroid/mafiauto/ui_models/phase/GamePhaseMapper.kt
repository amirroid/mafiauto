package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel

fun GamePhase.toUiModel() = when (this) {
    is GamePhase.Day -> GamePhaseUiModel.Day
    is GamePhase.Night -> GamePhaseUiModel.Night
    is GamePhase.Voting -> GamePhaseUiModel.Voting
    is GamePhase.Result -> GamePhaseUiModel.Result
    is GamePhase.Fate -> GamePhaseUiModel.Fate(targetPlayer = targetPlayer.toUiModel())
    is GamePhase.LastCard -> GamePhaseUiModel.LastCard(player = player.toUiModel())
    is GamePhase.Defending -> GamePhaseUiModel.Defending(
        defenders = defenders.map { it.toUiModel() }
    )
}