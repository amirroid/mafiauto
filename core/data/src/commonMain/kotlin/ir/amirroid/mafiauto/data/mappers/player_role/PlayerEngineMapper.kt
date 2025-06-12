package ir.amirroid.mafiauto.data.mappers.player_role

import ir.amirroid.mafiauto.data.mappers.player.toPlayerDomain
import ir.amirroid.mafiauto.data.mappers.role.toDescriptor
import ir.amirroid.mafiauto.game.engine.models.Player as EnginePlayer
import ir.amirroid.mafiauto.domain.model.PlayerWithRole

fun EnginePlayer.toPlayerRoleDomain(): PlayerWithRole {
    return PlayerWithRole(
        player = toPlayerDomain(),
        role = role.toDescriptor()
    )
}