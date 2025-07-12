package ir.amirroid.mafiauto.data.mappers.player

import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import ir.amirroid.mafiauto.domain.model.player.Player

fun PlayerEntity.toDomain(): Player {
    return Player(
        id = id,
        name = name
    )
}