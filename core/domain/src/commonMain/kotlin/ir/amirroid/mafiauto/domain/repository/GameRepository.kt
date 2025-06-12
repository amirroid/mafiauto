package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface GameRepository {
    val statusChecksCount: StateFlow<Int>

    fun startNewGame(players: List<PlayerWithRole>)

    fun getAllPlayers(): Flow<List<PlayerWithRole>>

    fun onStatusChecked()
    fun undoStatusCheck()

    fun kickPlayer(playerId: Long)
    fun unKickPlayer(playerId: Long)
}