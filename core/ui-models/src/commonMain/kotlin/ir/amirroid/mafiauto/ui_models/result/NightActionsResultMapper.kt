package ir.amirroid.mafiauto.ui_models.result

import ir.amirroid.mafiauto.domain.model.NightActionsResult

fun NightActionsResult.toUiModel() = NightActionsResultUiModel(
    deathCount = deathCount,
    revivedCount = revivedCount
)