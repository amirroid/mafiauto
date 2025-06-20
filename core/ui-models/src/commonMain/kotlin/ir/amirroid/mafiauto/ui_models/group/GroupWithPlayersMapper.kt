package ir.amirroid.mafiauto.ui_models.group

import ir.amirroid.mafiauto.domain.model.GroupWithPlayers
import ir.amirroid.mafiauto.ui_models.player.toUiModel

fun GroupWithPlayers.toUiModel() = GroupWithPlayersUiModel(
    groupName = group.name,
    groupId = group.id,
    players = players.map { it.toUiModel() }
)