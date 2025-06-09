package ir.amirroid.mafiauto.data.repository

import ir.amirroid.mafiauto.data.mappers.role.toDescriptor
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.domain.repository.RoleRepository
import ir.amirroid.mafiauto.game.engine.provider.RolesProvider

class RoleRepositoryImpl(private val rolesProvider: RolesProvider) : RoleRepository {
    override fun getAllRoles(): List<RoleDescriptor> {
        return rolesProvider.getAllRoles().map { it.toDescriptor() }
    }
}