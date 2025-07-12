package ir.amirroid.mafiauto.ui_models.last_card

import ir.amirroid.mafiauto.domain.model.game.LastCardDescriptor

fun LastCardDescriptor.toUiModel() = LastCardUiModel(
    name = name,
    explanation = explanation,
    key = key,
    targetCount = targetCount
)