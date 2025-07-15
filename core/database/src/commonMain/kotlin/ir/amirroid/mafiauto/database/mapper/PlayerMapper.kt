package ir.amirroid.mafiauto.database.mapper

import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import ir.amirroid.mafiauto.migrations.Player

fun Player.toEntity() = PlayerEntity(
    id = id,
    name = name,
)