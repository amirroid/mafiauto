package ir.amirroid.mafiauto.game.engine.utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import ir.amirroid.mafiauto.game.engine.data.Player

interface PlayersHolder {
    val players: SnapshotStateList<Player>
}