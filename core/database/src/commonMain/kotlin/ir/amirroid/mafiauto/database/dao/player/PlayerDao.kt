package ir.amirroid.mafiauto.database.dao.player

import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import kotlinx.coroutines.flow.Flow

interface PlayerDao {
    suspend fun getAll(): Flow<List<PlayerEntity>>
}