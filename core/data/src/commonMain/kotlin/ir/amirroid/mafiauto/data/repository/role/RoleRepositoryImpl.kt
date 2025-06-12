package ir.amirroid.mafiauto.data.repository.role

import ir.amirroid.mafiauto.data.mappers.role.toDescriptor
import ir.amirroid.mafiauto.data.memory.RoleMemoryHolder
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.domain.repository.RoleRepository
import ir.amirroid.mafiauto.game.engine.provider.roles.RolesProvider

class RoleRepositoryImpl(
    private val rolesProvider: RolesProvider,
    private val roleMemoryHolder: RoleMemoryHolder
) : RoleRepository {
    override fun getAllRoles(): List<RoleDescriptor> {
        return rolesProvider.getAllRoles().map { it.toDescriptor() }
    }

    override fun getAllSelectedRoles(): List<RoleDescriptor> {
        return roleMemoryHolder.value
    }

    override fun selectRoles(newRoles: List<RoleDescriptor>) {
        roleMemoryHolder.setValue(newRoles)
    }
}