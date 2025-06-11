package ir.amirroid.mafiauto.ui_models.player

import ir.amirroid.mafiauto.domain.model.Player

fun Player.toUiModel() = PlayerUiModel(
    name = name,
    id = id
)