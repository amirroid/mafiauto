package ir.amirroid.mafiauto.database.dao.player

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import ir.amirroid.mafiauto.PlayerEntityQueries
import ir.amirroid.mafiauto.database.mapper.toEntity
import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerDaoImpl(
    private val queries: PlayerEntityQueries,
    private val dispatcher: CoroutineDispatcher
) : PlayerDao {
    override fun getAllByGroupId(groupId: Long): Flow<List<PlayerEntity>> {
        return queries.getAllPlayersByGroupId(groupId)
            .asFlow().mapToList(dispatcher)
            .map { players ->
                players.map { it.toEntity() }
            }
    }

    override suspend fun addPlayer(name: String, groupId: Long) {
        queries.addPlayer(name, groupId)
    }

    override suspend fun deletePlayer(id: Long) {
        queries.deletePlayer(id)
    }
}