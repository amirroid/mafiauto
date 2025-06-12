package ir.amirroid.mafiauto.data.mappers.player

import ir.amirroid.mafiauto.game.engine.models.Player as EnginePlayer
import ir.amirroid.mafiauto.domain.model.Player
import ir.amirroid.mafiauto.game.engine.role.Role

fun Player.toEngine(
    role: Role
): EnginePlayer {
    return EnginePlayer(
        id = id,
        name = name,
        role = role
    )
}