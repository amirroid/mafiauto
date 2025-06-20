package ir.amirroid.mafiauto.database.mapper

import ir.amirroid.mafiauto.Player
import ir.amirroid.mafiauto.database.models.player.PlayerEntity

fun Player.toEntity() = PlayerEntity(
    id = id,
    name = name,
)