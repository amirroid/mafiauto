package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.game.engine.actions.role.*
import ir.amirroid.mafiauto.resources.Resources

data object GodFather : Role {
    override val key = RoleKeys.GOD_FATHER
    override val name = Resources.strings.godFather
    override val explanation = Resources.strings.godFatherExplanation
    override val alignment = Alignment.Mafia
    override fun getNightAction() = null
}

data object Mafia : Role {
    override val key = RoleKeys.MAFIA
    override val name = Resources.strings.mafia
    override val explanation = Resources.strings.mafiaExplanation
    override val alignment = Alignment.Mafia
    override fun getNightAction() = null
}