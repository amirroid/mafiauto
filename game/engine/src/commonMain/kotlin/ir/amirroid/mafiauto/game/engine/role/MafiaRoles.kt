package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.game.engine.actions.role.*

data class GodFather(val targetId: Int?) : Role {
    override val key = RoleKeys.GOD_FATHER
    override val englishName = "Godfather"
    override val persianName = "پدرخوانده"
    override val alignment = Alignment.Mafia
    override fun getNightAction() = targetId?.let { KillAction(key, it) }
}

data class Mafia(val targetId: Int?) : Role {
    override val key = RoleKeys.MAFIA
    override val englishName = "Mafia"
    override val persianName = "مافیا"
    override val alignment = Alignment.Mafia
    override fun getNightAction() = null
}