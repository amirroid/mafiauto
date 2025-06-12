package ir.amirroid.mafiauto.game.engine.provider

import ir.amirroid.mafiauto.game.engine.role.*
import ir.amirroid.mafiauto.game.engine.role.Role

class RolesProvider {
    fun getAllRoles(): List<Role> {
        return listOf(
            GodFather,
            Mafia,
            Joker,
            CultLeader,
            Doctor,
            Detective,
            Civilian,
            Sniper,
            Silencer,
            Bomber,
            Bulletproof,
            Mayor,
            Oracle
        )
    }

    fun findRole(key: String) = getAllRoles().first { it.key == key }
}