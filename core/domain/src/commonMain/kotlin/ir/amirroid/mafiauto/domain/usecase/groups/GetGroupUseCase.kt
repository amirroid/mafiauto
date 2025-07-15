package ir.amirroid.mafiauto.domain.usecase.groups

import ir.amirroid.mafiauto.domain.repository.GroupsRepository

class GetGroupUseCase(
    private val groupsRepository: GroupsRepository
) {
    operator fun invoke(groupId: Long) = groupsRepository.getGroup(groupId)
}