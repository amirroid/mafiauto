package ir.amirroid.mafiauto.data.repository.player

import ir.amirroid.mafiauto.data.mappers.player.toDomain
import ir.amirroid.mafiauto.database.dao.player.PlayerDao
import ir.amirroid.mafiauto.domain.model.Player
import ir.amirroid.mafiauto.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val playerDao: PlayerDao
) : PlayerRepository {
    override fun getAllPlayers(): Flow<List<Player>> {
        return playerDao.getAll().map { playerList ->
            playerList.map { it.toDomain() }
        }
    }

    override suspend fun addPlayer(name: String) {
        playerDao.addPlayer(name)
    }

    override suspend fun deletePlayer(id: Long) {
        playerDao.deletePlayer(id)
    }
}