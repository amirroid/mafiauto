package ir.amirroid.mafiauto.database.dao.player

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import ir.amirroid.mafiauto.PlayerEntityQueries
import ir.amirroid.mafiauto.database.models.player.PlayerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class PlayerDaoImpl(
    private val queries: PlayerEntityQueries,
    private val dispatcher: CoroutineDispatcher
) : PlayerDao {
    override suspend fun getAll(): Flow<List<PlayerEntity>> {
        return queries.getAllPlayers { id, name ->
            PlayerEntity(id, name)
        }.asFlow().mapToList(dispatcher)
    }
}