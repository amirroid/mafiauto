package ir.amirroid.mafiauto.data.repository.game

import ir.amirroid.mafiauto.data.mappers.last_card.toDomain
import ir.amirroid.mafiauto.data.mappers.phase.toDomain
import ir.amirroid.mafiauto.data.mappers.player.toEngine
import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.domain.model.LastCardDescriptor
import ir.amirroid.mafiauto.domain.model.NightActionDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.domain.repository.GameRepository
import ir.amirroid.mafiauto.game.engine.GameEngine
import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.roles.RolesProvider
import ir.amirroid.mafiauto.game.engine.role.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.resources.StringResource

class GameRepositoryImpl(
    private val engine: GameEngine,
    private val rolesProvider: RolesProvider
) : GameRepository {
    override val statusChecksCount = engine.statusCheckCount
    override val currentDay = engine.currentDay
    override val messages = engine.messages
    override val currentPhase = engine.currentPhase.map { it.toDomain() }
    override val lastCards = engine.lastCards.map { cards -> cards.map { it.toDomain() } }

    override fun startNewGame(players: List<PlayerWithRole>) {
        val enginePlayers = players.map {
            val role = rolesProvider.findRole(it.role.key)
            it.player.toEngine(role)
        }
        engine.updatePlayers(enginePlayers)
    }

    override fun resetGame() = engine.reset()

    override fun nextPhase() = engine.proceedToNextPhase()

    override fun startDefending(players: List<PlayerWithRole>) =
        engine.proceedToDefendingPhase(getEnginePlayersFromDomain(players))

    override fun getAllPlayers(): Flow<List<PlayerWithRole>> {
        return engine.players.map { players ->
            players.map { it.toPlayerRoleDomain() }
        }
    }

    override fun handleDefenseVoteResult(voteMap: Map<PlayerWithRole, Int>) {
        engine.handleDefenseVoteResult(
            voteMap.mapKeys { (player, _) -> player.toEngine() }
        )
    }

    override fun getDefenseCandidates(playerVotes: Map<PlayerWithRole, Int>): List<PlayerWithRole> {
        return engine
            .getDefenseCandidates(playerVotes.mapKeys { (player, _) -> player.toEngine() })
            .map { it.toPlayerRoleDomain() }
    }

    override fun handleNightActions(actions: List<NightActionDescriptor>) {
        engine.handleNightActions(actions.map { it.toEngine() })
    }

    override fun handleFate() = engine.handleFatePhase()

    override fun applyLastCard(card: LastCardDescriptor, pickedPlayers: List<PlayerWithRole>) {
        engine.applyLastCard(card.toEngine(), getEnginePlayersFromDomain(pickedPlayers))
    }

    override fun onStatusChecked() = engine.incrementStatusCheckCount()
    override fun undoStatusCheck() = engine.decreaseStatusCheckCount()

    override fun kickPlayer(playerId: Long) = engine.kickPlayer(playerId)
    override fun unKickPlayer(playerId: Long) = engine.unKickPlayer(playerId)

    private fun getEnginePlayersFromDomain(players: List<PlayerWithRole>): List<Player> {
        val ids = players.map { it.player.id }
        return engine.players.value.filter { it.id in ids }
    }

    private fun PlayerWithRole.toEngine(): Player {
        return engine.players.value.first { it.id == player.id }
    }

    private fun LastCardDescriptor.toEngine(): LastCard {
        return engine.lastCards.value.first { it.key == key }
    }

    private fun NightActionDescriptor.toEngine(): NightAction {
        return NightAction(
            player = player.toEngine(),
            targets = targets.map { it.toEngine() },
            replacementRole = replacementRole?.let { rolesProvider.findRole(it.key) }
        )
    }
}