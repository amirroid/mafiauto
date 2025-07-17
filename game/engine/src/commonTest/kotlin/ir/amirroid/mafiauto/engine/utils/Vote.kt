package ir.amirroid.mafiauto.engine.utils

import ir.amirroid.mafiauto.game.engine.models.Player
import kotlin.random.Random

fun generateVotes(players: List<Player>): Map<Player, Player> {
    val target = players.last()
    val votes = players.take(players.size / 2 + 1).associateWith { target }.toMutableMap()
    votes[players.last()] = players.first()
    return votes
}

fun generateDefenseVotes(players: List<Player>, defender: Player): Map<Player, Int> {
    val threshold = (players.size / 2) - 1
    val votes = mutableMapOf<Player, Int>()
    for (player in players) {
        if (player == defender) continue
        votes[player] = if (threshold == 0) 0 else Random.nextInt(0, threshold)
    }
    votes[defender] = players.size.div(2) + 1
    return votes
}