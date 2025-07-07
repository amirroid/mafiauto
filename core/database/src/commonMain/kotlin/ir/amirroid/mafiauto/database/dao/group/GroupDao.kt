package ir.amirroid.mafiauto.database.dao.group

import ir.amirroid.mafiauto.database.models.group_with_players.GroupWithPlayers
import kotlinx.coroutines.flow.Flow

interface GroupDao {
    fun getAllGroups(): Flow<List<GroupWithPlayers>>
    suspend fun addNewGroup(name: String)
    suspend fun editGroupName(groupId: Long, newName: String)
    suspend fun deleteGroup(groupId: Long)
}