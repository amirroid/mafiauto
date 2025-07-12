package ir.amirroid.mafiauto.domain.model.group

import ir.amirroid.mafiauto.domain.model.player.Player

data class GroupWithPlayers(
    val group: Group, val players: List<Player>
)