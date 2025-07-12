package ir.amirroid.mafiauto.data.mappers.night_target_options

import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.data.mappers.resource.toDomain
import ir.amirroid.mafiauto.domain.model.game.NightTargetOptions
import ir.amirroid.mafiauto.game.engine.models.NightTargetOptions as EngineNightTargetOptions

fun EngineNightTargetOptions.toDomain() = NightTargetOptions(
    player = player.toPlayerRoleDomain(),
    availableTargets = availableTargets.map { it.toPlayerRoleDomain() },
    message = message?.toDomain(),
    isReplacement = isReplacement
)