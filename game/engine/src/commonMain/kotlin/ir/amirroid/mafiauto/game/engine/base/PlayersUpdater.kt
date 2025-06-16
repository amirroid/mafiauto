package ir.amirroid.mafiauto.game.engine.base

import ir.amirroid.mafiauto.game.engine.models.Player

interface PlayersUpdater {
    fun updatePlayers(newPlayers: List<Player>)
}