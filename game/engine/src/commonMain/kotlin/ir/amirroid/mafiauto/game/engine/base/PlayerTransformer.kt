package ir.amirroid.mafiauto.game.engine.base

import ir.amirroid.mafiauto.game.engine.models.Player

interface PlayerTransformer {
    fun updatePlayer(
        players: List<Player>,
        targetId: Long,
        transform: Player.() -> Player
    ): List<Player> {
        return players.toMutableList().apply {
            val index = indexOfFirst { it.id == targetId }
            if (index != -1) this[index] = this[index].transform()
        }
    }
}