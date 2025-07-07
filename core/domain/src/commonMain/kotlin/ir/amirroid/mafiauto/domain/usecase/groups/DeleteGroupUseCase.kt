package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class DeleteGroupUseCase(
    private val groupsRepository: GroupsRepository
) {
    suspend operator fun invoke(groupId: Long) = groupsRepository.deleteGroup(groupId)
}