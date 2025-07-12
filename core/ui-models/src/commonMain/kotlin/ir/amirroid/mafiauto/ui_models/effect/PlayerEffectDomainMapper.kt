package ir.amirroid.mafiauto.ui_models.effect

import ir.amirroid.mafiauto.domain.model.effect.PlayerEffectDomain
import ir.amirroid.mafiauto.game.engine.utils.DayActionNames
import ir.amirroid.mafiauto.resources.Resources

fun PlayerEffectDomain.toUiModel() = when (this) {
    is PlayerEffectDomain.DayActionEffect -> {
        PlayerEffectUiModel.DayActionEffect(
            name = name,
            nightActionRequiredPicks = nightActionRequiredPicks,
            info = findInfoForDayAction(name)
        )
    }

    is PlayerEffectDomain.FlagEffect -> PlayerEffectUiModel.FlagEffect(flag, findInfoForFlag(flag))
}

private fun findInfoForDayAction(name: String): EffectButtonInfo? = when (name) {
    DayActionNames.GUNSHOT -> EffectButtonInfo.ResourceIcon(
        icon = Resources.drawable.gun,
        text = Resources.strings.gunShot
    )

    else -> null
}

private fun findInfoForFlag(flag: String): EffectButtonInfo? = when (flag) {
    else -> null
}