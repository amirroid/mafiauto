package ir.amirroid.mafiauto.game.engine.utils

import ir.amirroid.mafiauto.game.engine.data.Player

interface PlayersHolder {
    fun updatePlayers(newPlayers: List<Player>)
}