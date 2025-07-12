package ir.amirroid.mafiauto.game.engine.provider.roles

import ir.amirroid.mafiauto.game.engine.role.*

class RolesProviderImpl : RolesProvider {
    override fun getAllRoles(): List<Role> {
        return listOf(
            GodFather,
            Mafia,
            SaulGoodman,
            Joker,
            Detective,
            Doctor,
            Sniper,
            Gunsmith,
            Silencer,
            Ranger,
            Surgeon,
            Bomber,
            Bulletproof,
            Mayor,
            Civilian(1),
            Civilian(2),
            Civilian(3),
            Civilian(4),
            Civilian(5),
            Nostradamus
        )
    }

    override fun findRole(key: String) = getAllRoles().first { it.key == key }
}