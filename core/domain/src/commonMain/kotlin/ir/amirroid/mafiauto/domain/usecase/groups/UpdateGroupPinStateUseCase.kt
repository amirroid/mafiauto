package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class UpdateGroupPinStateUseCase(
    private val groupsRepository: GroupsRepository
) {
    suspend operator fun invoke(groupId: Long, isPinned: Boolean) =
        groupsRepository.updateGroupPinState(groupId, isPinned)
}