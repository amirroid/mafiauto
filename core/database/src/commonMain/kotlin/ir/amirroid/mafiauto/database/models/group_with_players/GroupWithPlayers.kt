package ir.amirroid.mafiauto.database.models.group_with_players

import ir.amirroid.mafiauto.database.models.group.GroupEntity
import ir.amirroid.mafiauto.database.models.player.PlayerEntity

data class GroupWithPlayers(
    val group: GroupEntity,
    val players: List<PlayerEntity>
)
