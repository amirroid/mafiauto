package ir.amirroid.mafiauto.data.repository.group

import ir.amirroid.mafiauto.data.mappers.group.toDomain
import ir.amirroid.mafiauto.database.dao.group.GroupDao
import ir.amirroid.mafiauto.domain.model.GroupWithPlayers
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
        groupDao.addNewGroup(name)
    }
}