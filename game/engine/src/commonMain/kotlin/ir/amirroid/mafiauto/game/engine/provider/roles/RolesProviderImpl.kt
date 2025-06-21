package ir.amirroid.mafiauto.game.engine.provider.roles

import ir.amirroid.mafiauto.game.engine.role.*

class RolesProviderImpl : RolesProvider {
    override fun getAllRoles(): List<Role> {
        return listOf(
            GodFather,
            Mafia,
            Joker,
            CultLeader,
            Detective,
            Doctor,
            Civilian,
            Sniper,
            Silencer,
            Surgeon,
            Bomber,
            Bulletproof,
            Mayor,
            Nostradamus
        )
    }

    override fun findRole(key: String) = getAllRoles().first { it.key == key }
}