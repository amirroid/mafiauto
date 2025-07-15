package ir.amirroid.mafiauto.data.repository.group

import ir.amirroid.mafiauto.data.mappers.group.toDomain
import ir.amirroid.mafiauto.database.dao.group.GroupDao
import ir.amirroid.mafiauto.domain.model.group.Group
import ir.amirroid.mafiauto.domain.model.group.GroupWithPlayers
import ir.amirroid.mafiauto.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupsRepositoryImpl(
    private val groupDao: GroupDao
) : GroupsRepository {
    override fun getAllGroups(): Flow<List<GroupWithPlayers>> {
        return groupDao.getAllGroups().map { groups ->
            groups.map { it.toDomain() }
        }
    }

    override suspend fun addNewGroup(name: String) {
        return groupDao.addNewGroup(name)
    }

    override suspend fun editGroupName(groupId: Long, newName: String) {
        return groupDao.editGroupName(groupId, newName)
    }

    override suspend fun deleteGroup(groupId: Long) {
        return groupDao.deleteGroup(groupId)
    }

    override suspend fun updateGroupPinState(groupId: Long, isPinned: Boolean) {
        return groupDao.updateGroupPinState(groupId, isPinned)
    }

    override fun getGroup(groupId: Long): Flow<Group> {
        return groupDao.getGroup(groupId).map { it.toDomain() }
    }
}