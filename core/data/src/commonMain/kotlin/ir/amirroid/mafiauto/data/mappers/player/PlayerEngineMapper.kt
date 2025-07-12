package ir.amirroid.mafiauto.data.mappers.player

import ir.amirroid.mafiauto.data.mappers.effect.toDomain
import ir.amirroid.mafiauto.game.engine.models.Player as EnginePlayer
import ir.amirroid.mafiauto.domain.model.player.Player

fun EnginePlayer.toPlayerDomain(): Player {
    return Player(
        id = id,
        name = name,
        isAlive = isAlive,
        isKick = isKick,
        isSilenced = isSilenced,
        canUseAbility = canUseAbility,
        remainingAbilityUses = remainingAbilityUses,
        effects = effects.map { it.toDomain() }
    )
}