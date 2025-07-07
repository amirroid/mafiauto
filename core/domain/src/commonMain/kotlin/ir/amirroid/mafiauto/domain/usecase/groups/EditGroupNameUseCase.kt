package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class EditGroupNameUseCase(
    private val groupsRepository: GroupsRepository
) {
    suspend operator fun invoke(groupId: Long, newName: String) =
        groupsRepository.editGroupName(groupId, newName)
}