package ir.amirroid.mafiauto.domain.model

sealed interface GamePhase {
    data object Day : GamePhase
    data object Voting : GamePhase
    data object FinalDebate : GamePhase
    data class Defending(val defenders: List<PlayerWithRole>) : GamePhase
    data class LastCard(val player: PlayerWithRole) : GamePhase
    data class Fate(
        val targetPlayer: PlayerWithRole,
        val sameVotesDefenders: List<PlayerWithRole>
    ) : GamePhase

    data class Night(val options: List<NightTargetOptions>) : GamePhase
    data class Result(val result: NightActionsResult) : GamePhase
    data class End(val winnerAlignment: Alignment) : GamePhase
}