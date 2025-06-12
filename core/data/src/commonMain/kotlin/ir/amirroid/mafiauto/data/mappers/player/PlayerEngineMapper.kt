package ir.amirroid.mafiauto.data.mappers.player

import ir.amirroid.mafiauto.game.engine.data.Player as EnginePlayer
import ir.amirroid.mafiauto.domain.model.Player

fun EnginePlayer.toPlayerDomain(): Player {
    return Player(
        id = id,
        name = name,
        isAlive = isAlive,
        isKick = isKick
    )
}