package ir.amirroid.mafiauto.game.engine.actions

import androidx.compose.runtime.snapshots.SnapshotStateList
import ir.amirroid.mafiauto.game.engine.data.Player
import ir.amirroid.mafiauto.game.engine.data.findWithId

abstract class GameActions {
    abstract val players: SnapshotStateList<Player>

    fun kill(playerId: Int) {
        players.findWithId(playerId)?.let { it.isAlive = false }
    }

    fun save(playerId: Int) {
        players.findWithId(playerId)?.let { it.isAlive = true }
    }
}