package ir.amirroid.mafiauto.domain.usecase.role

import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor
import ir.amirroid.mafiauto.domain.repository.RoleRepository

class SelectNewRolesUseCase(
    private val rolesRepository: RoleRepository
) {
    operator fun invoke(newRoles: List<RoleDescriptor>) = rolesRepository.selectRoles(newRoles)
}