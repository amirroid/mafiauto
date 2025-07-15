package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.group.Group
import ir.amirroid.mafiauto.domain.model.group.GroupWithPlayers
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {
    fun getAllGroups(): Flow<List<GroupWithPlayers>>
    suspend fun addNewGroup(name: String)
    suspend fun editGroupName(groupId: Long, newName: String)
    suspend fun deleteGroup(groupId: Long)
    suspend fun updateGroupPinState(groupId: Long, isPinned: Boolean)
    fun getGroup(groupId: Long): Flow<Group>
}