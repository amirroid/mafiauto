package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.ConvertAction
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys

class Joker : Role {
    override val key = RoleKeys.JOKER
    override val englishName = "Joker"
    override val persianName = "جوکر"
    override val alignment = Alignment.Neutral
    override fun getNightAction() = null
}

data class CultLeader(val targetId: Int?) : Role {
    override val key = RoleKeys.CULT_LEADER
    override val englishName = "Cult Leader"
    override val persianName = "رهبر فرقه"
    override val alignment = Alignment.Neutral
    override fun getNightAction() = targetId?.let { ConvertAction(key, it, key) }
}