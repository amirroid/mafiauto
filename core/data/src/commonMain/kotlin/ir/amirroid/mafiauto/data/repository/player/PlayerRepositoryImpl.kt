package ir.amirroid.mafiauto.data.repository.player

import ir.amirroid.mafiauto.data.memory.PlayerMemoryHolder
import ir.amirroid.mafiauto.data.mappers.player.toDomain
import ir.amirroid.mafiauto.database.dao.player.PlayerDao
import ir.amirroid.mafiauto.domain.model.Player
import ir.amirroid.mafiauto.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val playerDao: PlayerDao,
    private val playerMemoryHolder: PlayerMemoryHolder
) : PlayerRepository {
    override fun getAllPlayers(groupId: Long): Flow<List<Player>> {
        return playerDao.getAllByGroupId(groupId).map { playerList ->
            playerList.map { it.toDomain() }
        }
    }

    override suspend fun addPlayer(name: String, groupId: Long) {
        playerDao.addPlayer(name, groupId)
    }

    override suspend fun deletePlayer(id: Long) {
        playerDao.deletePlayer(id)
    }

    override fun getAllSelectedPlayers(): List<Player> {
        return playerMemoryHolder.value
    }

    override fun selectPlayers(newPlayers: List<Player>) {
        playerMemoryHolder.setValue(newPlayers)
    }
}