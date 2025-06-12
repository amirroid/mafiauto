package ir.amirroid.mafiauto.game.engine.provider.roles

import ir.amirroid.mafiauto.game.engine.role.Bomber
import ir.amirroid.mafiauto.game.engine.role.Bulletproof
import ir.amirroid.mafiauto.game.engine.role.Civilian
import ir.amirroid.mafiauto.game.engine.role.CultLeader
import ir.amirroid.mafiauto.game.engine.role.Detective
import ir.amirroid.mafiauto.game.engine.role.Doctor
import ir.amirroid.mafiauto.game.engine.role.GodFather
import ir.amirroid.mafiauto.game.engine.role.Joker
import ir.amirroid.mafiauto.game.engine.role.Mafia
import ir.amirroid.mafiauto.game.engine.role.Mayor
import ir.amirroid.mafiauto.game.engine.role.Oracle
import ir.amirroid.mafiauto.game.engine.role.Role
import ir.amirroid.mafiauto.game.engine.role.Silencer
import ir.amirroid.mafiauto.game.engine.role.Sniper

class RolesProviderImpl : RolesProvider {
    override fun getAllRoles(): List<Role> {
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
    override fun findRole(key: String) = getAllRoles().first { it.key == key }
}