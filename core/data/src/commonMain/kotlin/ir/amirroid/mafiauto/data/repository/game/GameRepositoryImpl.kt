package ir.amirroid.mafiauto.data.repository.game

import ir.amirroid.mafiauto.data.mappers.player.toEngine
import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository
import ir.amirroid.mafiauto.game.engine.GameEngine
import ir.amirroid.mafiauto.game.engine.provider.RolesProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepositoryImpl(
    private val engine: GameEngine,
    private val rolesProvider: RolesProvider
) : GameRepository {
    override val statusChecksCount = engine.statusCheckCount

    override fun startNewGame(players: List<PlayerWithRole>) {
        val enginePlayers = players.map {
            val role = rolesProvider.findRole(it.role.key)
            it.player.toEngine(role)
        }
        engine.updatePlayers(enginePlayers)
    }

    override fun getAllPlayers(): Flow<List<PlayerWithRole>> {
        return engine.players.map { players -> players.map { it.toPlayerRoleDomain() } }
    }

    override fun onStatusChecked() = engine.incrementStatusCheckCount()
    override fun undoStatusCheck() = engine.decreaseStatusCheckCount()

    override fun kickPlayer(playerId: Long) = engine.kickPlayer(playerId)
    override fun unKickPlayer(playerId: Long) = engine.unKickPlayer(playerId)
}