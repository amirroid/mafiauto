package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class GetAllGroupsUseCase(private val groupsRepository: GroupsRepository) {
    operator fun invoke() = groupsRepository.getAllGroups()
}