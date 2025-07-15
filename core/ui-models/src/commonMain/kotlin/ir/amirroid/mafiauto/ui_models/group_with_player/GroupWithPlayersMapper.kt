package ir.amirroid.mafiauto.ui_models.group_with_player

import ir.amirroid.mafiauto.domain.model.group.GroupWithPlayers

fun GroupWithPlayers.toUiModel() = GroupWithPlayersUiModel(
    groupName = group.name,
    groupId = group.id,
    formatedPlayersList = players.joinToString("\n") { it.name },
    isPinnedGroup = group.isPinned
)