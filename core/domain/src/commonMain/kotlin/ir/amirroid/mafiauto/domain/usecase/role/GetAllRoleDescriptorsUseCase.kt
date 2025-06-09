package ir.amirroid.mafiauto.domain.usecase.role

import ir.amirroid.mafiauto.domain.repository.RoleRepository

class GetAllRoleDescriptorsUseCase(private val roleRepository: RoleRepository) {
    operator fun invoke() = roleRepository.getAllRoles()
}