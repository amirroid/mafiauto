package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getAllPlayers(): Flow<List<Player>>
    suspend fun addPlayer(name: String)
    suspend fun deletePlayer(id: Long)

    fun getAllSelectedPlayers(): List<Player>
    fun selectPlayers(newPlayers: List<Player>)
}