package ir.amirroid.mafiauto.data.mappers.action

import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.data.mappers.role.toDescriptor
import ir.amirroid.mafiauto.domain.model.game.NightActionDescriptor
import ir.amirroid.mafiauto.game.engine.models.NightAction

fun NightAction.toDomain() = NightActionDescriptor(
    player = player.toPlayerRoleDomain(),
    targets = targets.map { it.toPlayerRoleDomain() },
    replacementRole = replacementRole?.toDescriptor()
)