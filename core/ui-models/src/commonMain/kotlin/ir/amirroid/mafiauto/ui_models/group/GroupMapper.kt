package ir.amirroid.mafiauto.ui_models.group

import ir.amirroid.mafiauto.domain.model.group.Group

fun Group.toUiModel() = GroupUiModel(
    isPinned = isPinned,
    name = name
)