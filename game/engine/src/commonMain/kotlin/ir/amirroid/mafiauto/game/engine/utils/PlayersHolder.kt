package ir.amirroid.mafiauto.game.engine.utils

import ir.amirroid.mafiauto.game.engine.data.Player
import kotlinx.coroutines.flow.MutableStateFlow

interface PlayersHolder {
    val _players: MutableStateFlow<List<Player>>
}