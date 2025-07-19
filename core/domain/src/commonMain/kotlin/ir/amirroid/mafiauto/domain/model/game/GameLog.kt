package ir.amirroid.mafiauto.domain.model.game

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole

sealed class GameLog(val day: Int) {
    data class NightActions(
        val actions: List<NightActionDescriptor>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class DefenseVotes(
        val playerVotes: Map<PlayerWithRole, Int>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class ElectedInDefense(
        val player: PlayerWithRole,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class ApplyLastCard(
        val player: PlayerWithRole,
        val card: LastCardDescriptor,
        val selectedPlayers: List<PlayerWithRole>,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class Kick(
        val player: PlayerWithRole,
        val isKicked: Boolean,
        val relatedDay: Int
    ) : GameLog(relatedDay)

    data class FinalDebate(
        val playersTrustVotes: Map<PlayerWithRole, PlayerWithRole>,
        val relatedDay: Int
    ) : GameLog(relatedDay)
}
