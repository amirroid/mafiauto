package ir.amirroid.mafiauto.domain.usecase.role

import ir.amirroid.mafiauto.domain.repository.RoleRepository

class GetSingleRoleDescriptorUseCase(
    private val roleRepository: RoleRepository
) {
    operator fun invoke(key: String) = roleRepository.getRole(key)
}