package ir.amirroid.mafiauto.ui_models.phase

import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.resources.Resources

fun GamePhase.toUiModel(): GamePhaseUiModel {
    return GamePhaseUiModel.valueOf(name)
}