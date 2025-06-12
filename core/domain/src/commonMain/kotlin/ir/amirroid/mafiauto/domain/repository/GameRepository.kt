package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface GameRepository {
    val statusChecksCount: StateFlow<Int>
    val currentPhase: Flow<GamePhase>

    fun startNewGame(players: List<PlayerWithRole>)
    fun resetGame()

    fun getAllPlayers(): Flow<List<PlayerWithRole>>

    fun nextPhase()
    fun startDefending(players: List<PlayerWithRole>)

    fun onStatusChecked()
    fun undoStatusCheck()

    fun kickPlayer(playerId: Long)
    fun unKickPlayer(playerId: Long)

    fun getDefenseCandidates(playerVotes: Map<PlayerWithRole, Int>): List<PlayerWithRole>
}