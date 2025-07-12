package ir.amirroid.mafiauto.data.mappers.group

import ir.amirroid.mafiauto.data.mappers.player.toDomain
import ir.amirroid.mafiauto.database.models.group_with_players.GroupWithPlayers as GroupWithPlayersEntity
import ir.amirroid.mafiauto.domain.model.group.GroupWithPlayers

fun GroupWithPlayersEntity.toDomain() = GroupWithPlayers(
    group = group.toDomain(),
    players = players.map { it.toDomain() }
)