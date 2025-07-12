package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.player.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getAllPlayers(groupId: Long): Flow<List<Player>>
    suspend fun addPlayer(name: String, groupId: Long)
    suspend fun deletePlayer(id: Long)

    fun getAllSelectedPlayers(): List<Player>
    fun selectPlayers(newPlayers: List<Player>)
}