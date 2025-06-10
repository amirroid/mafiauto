package ir.amirroid.mafiauto.game.engine.actions

import androidx.compose.runtime.snapshots.SnapshotStateList
import ir.amirroid.mafiauto.game.engine.data.Player
import ir.amirroid.mafiauto.game.engine.data.findWithId
import ir.amirroid.mafiauto.game.engine.utils.PlayersHolder

abstract class GameActions : PlayersHolder {
    fun kill(playerId: Int) {
        players.findWithId(playerId)?.let { it.isAlive = false }
    }

    fun save(playerId: Int) {
        players.findWithId(playerId)?.let { it.isAlive = true }
    }
}