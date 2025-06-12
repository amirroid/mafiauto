package ir.amirroid.mafiauto.game.engine.provider.roles

import ir.amirroid.mafiauto.game.engine.role.Role

interface RolesProvider {
    fun getAllRoles(): List<Role>
    fun findRole(key: String): Role
}