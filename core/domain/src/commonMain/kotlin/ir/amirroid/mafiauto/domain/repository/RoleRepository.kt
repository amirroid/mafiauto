package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.RoleDescriptor

interface RoleRepository {
    fun getAllRoles(): List<RoleDescriptor>
}