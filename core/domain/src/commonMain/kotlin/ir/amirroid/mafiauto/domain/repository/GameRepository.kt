package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.domain.model.LastCardDescriptor
import ir.amirroid.mafiauto.domain.model.NightActionDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.StringResource


interface GameRepository {
    val statusChecksCount: StateFlow<Int>
    val currentDay: StateFlow<Int>
    val messages: Flow<StringResource>
    val currentPhase: Flow<GamePhase>
    val lastCards: Flow<List<LastCardDescriptor>>

    fun startNewGame(players: List<PlayerWithRole>)
    fun resetGame()

    fun getAllPlayers(): Flow<List<PlayerWithRole>>

    fun nextPhase()
    fun startDefending(players: List<PlayerWithRole>)

    fun onStatusChecked()
    fun undoStatusCheck()

    fun kickPlayer(playerId: Long)
    fun unKickPlayer(playerId: Long)

    fun handleDefenseVoteResult(voteMap: Map<PlayerWithRole, Int>)

    fun getDefenseCandidates(playerVotes: Map<PlayerWithRole, Int>): List<PlayerWithRole>

    fun handleNightActions(actions: List<NightActionDescriptor>)
    fun handleFate()

    fun applyLastCard(card: LastCardDescriptor, pickedPlayers: List<PlayerWithRole>)
}