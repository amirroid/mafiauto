package ir.amirroid.mafiauto.ui_models.last_card

import ir.amirroid.mafiauto.domain.model.LastCardDescriptor

fun LastCardDescriptor.toUiModel() = LastCardUiModel(
    name = name,
    key = key
)