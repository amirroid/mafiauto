package ir.amirroid.mafiauto.database.dao.group

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
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
            .asFlow().mapToList(dispatcher)
            .map { groupWithPlayer ->
                groupWithPlayer.groupBy { GroupEntity(it.groupId, it.groupName) }
                    .map { (group, players) ->
                        GroupWithPlayers(
                            group = group,
                            players = players.mapNotNull {
                                if (it.playerId == null) return@mapNotNull null
                                PlayerEntity(it.playerId, it.name!!)
                            }
                        )
                    }
            }
    }

    override suspend fun addNewGroup(name: String) {
        queries.addNewGroup(name).await()
    }
}