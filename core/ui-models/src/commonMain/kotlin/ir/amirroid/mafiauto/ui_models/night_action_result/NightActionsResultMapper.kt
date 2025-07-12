package ir.amirroid.mafiauto.ui_models.night_action_result

import ir.amirroid.mafiauto.domain.model.game.NightActionsResult

fun NightActionsResult.toUiModel() = NightActionsResultUiModel(
    deathCount = deathCount,
    revivedCount = revivedCount
)