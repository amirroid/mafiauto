package ir.amirroid.mafiauto.game.engine.effect

import ir.amirroid.mafiauto.game.engine.actions.day.DayAction
import ir.amirroid.mafiauto.game.engine.models.Player

sealed interface PlayerEffect

data class FlagEffect(val flag: String) : PlayerEffect

interface DayActionEffect : PlayerEffect {
    val name: String
    val nightActionRequiredPicks: Int get() = 1
    fun getAction(): DayAction? = null
}