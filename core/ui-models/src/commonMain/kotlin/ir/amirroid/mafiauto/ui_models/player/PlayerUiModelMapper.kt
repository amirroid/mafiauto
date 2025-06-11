package ir.amirroid.mafiauto.ui_models.player

import ir.amirroid.mafiauto.domain.model.Player

fun PlayerUiModel.toDomain() = Player(
    name = name,
    id = id
)