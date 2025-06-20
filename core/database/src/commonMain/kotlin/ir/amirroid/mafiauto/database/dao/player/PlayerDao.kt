package ir.amirroid.mafiauto.database.dao.player

import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import kotlinx.coroutines.flow.Flow

interface PlayerDao {
    fun getAllByGroupId(groupId: Long): Flow<List<PlayerEntity>>
    suspend fun addPlayer(name: String, groupId: Long)
    suspend fun deletePlayer(id: Long)
}