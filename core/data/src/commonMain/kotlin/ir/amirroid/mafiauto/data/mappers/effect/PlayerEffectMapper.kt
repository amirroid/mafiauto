package ir.amirroid.mafiauto.data.mappers.effect

import ir.amirroid.mafiauto.domain.model.effect.PlayerEffectDomain
import ir.amirroid.mafiauto.game.engine.effect.DayActionEffect
import ir.amirroid.mafiauto.game.engine.effect.FlagEffect
import ir.amirroid.mafiauto.game.engine.effect.PlayerEffect

fun PlayerEffect.toDomain() = when (this) {
    is DayActionEffect -> {
        PlayerEffectDomain.DayActionEffect(
            name = name,
            nightActionRequiredPicks = nightActionRequiredPicks,
        )
    }

    is FlagEffect -> PlayerEffectDomain.FlagEffect(flag)
}