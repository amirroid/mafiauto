package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.ConvertAction
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources

data object Joker : Role {
    override val key = RoleKeys.JOKER
    override val name = Resources.strings.joker
    override val alignment = Alignment.Neutral
    override fun getNightAction() = null
}


data object CultLeader : Role {
    override val key = RoleKeys.CULT_LEADER
    override val name = Resources.strings.cultLeader
    override val alignment = Alignment.Neutral
    override fun getNightAction() = null
}