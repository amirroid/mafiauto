package ir.amirroid.mafiauto.database.dao.group

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import ir.amirroid.mafiauto.GroupEntityQueries
import ir.amirroid.mafiauto.database.models.group.GroupEntity
import ir.amirroid.mafiauto.database.models.group_with_players.GroupWithPlayers
import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupDaoImpl(
    private val queries: GroupEntityQueries,
    private val dispatcher: CoroutineDispatcher
) : GroupDao {
    override fun getAllGroups(): Flow<List<GroupWithPlayers>> {
        return queries.selectAllGroupsWithPlayers()
            .asFlow()
            .mapToList(dispatcher)
            .map { rows ->
                rows
                    .groupBy { GroupEntity(it.groupId, it.groupName, it.isPinndedGroup) }
                    .map { (group, players) ->
                        GroupWithPlayers(
                            group = group,
                            players = players.mapNotNull {
                                it.playerId?.let { id ->
                                    PlayerEntity(id, it.playerName!!)
                                }
                            }
                        )
                    }
            }
    }

    override suspend fun addNewGroup(name: String) {
        queries.addNewGroup(name)
    }

    override suspend fun editGroupName(groupId: Long, newName: String) {
        queries.updateGroupName(newName, groupId)
    }

    override suspend fun deleteGroup(groupId: Long) {
        queries.deleteGroupById(groupId)
    }

    override suspend fun updateGroupPinState(groupId: Long, isPinned: Boolean) {
        queries.updateGroupPinState(isPinned, groupId)
    }

    override fun getGroup(groupId: Long): Flow<GroupEntity> {
        return queries.selectGroupById(groupId).asFlow()
            .mapToOne(dispatcher).map {
                GroupEntity(id = it.id, name = it.name, isPinned = it.isPinned)
            }
    }
}