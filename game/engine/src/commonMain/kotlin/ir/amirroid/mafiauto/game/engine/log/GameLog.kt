package ir.amirroid.mafiauto.game.engine.log

import ir.amirroid.mafiauto.game.engine.last_card.LastCard
import ir.amirroid.mafiauto.game.engine.models.NightAction
import ir.amirroid.mafiauto.game.engine.models.Player

sealed class GameLog(val day: Int) {
    data class NightActions(
        val actions: List<NightAction>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class DefenseVotes(
        val playerVotes: Map<Player, Int>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class ElectedInDefense(
        val player: Player,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class ApplyLastCard(
        val player: Player,
        val card: LastCard,
        val selectedPlayers: List<Player>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class Kick(
        val player: Player,
        val isKicked: Boolean,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class FinalDebate(
        val playersTrustVotes: Map<Player, Player>,
        val relatedDay: Int
    ) : GameLog(relatedDay)
}