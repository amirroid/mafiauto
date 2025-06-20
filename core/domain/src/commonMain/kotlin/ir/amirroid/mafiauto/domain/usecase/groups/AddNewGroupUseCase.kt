package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class AddNewGroupUseCase(private val groupsRepository: GroupsRepository) {
    suspend operator fun invoke(name: String) = groupsRepository.addNewGroup(name)
}