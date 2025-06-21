package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources

data object Joker : Role {
    override val key = RoleKeys.JOKER
    override val name = Resources.strings.joker
    override val explanation = Resources.strings.jokerExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = false
    override fun getNightAction() = null
}

data object Nostradamus : Role {
    override val key = RoleKeys.NOSTRADAMUS
    override val name = Resources.strings.nostradamus
    override val explanation = Resources.strings.nostradamusExplanation
    override val alignment = Alignment.Neutral
    override val hasNightAction = true
    override val targetNightToWakingUp: Int = 0
    override val nightActionRequiredPicks: Int = 3

    override fun getNightAction() = null
}