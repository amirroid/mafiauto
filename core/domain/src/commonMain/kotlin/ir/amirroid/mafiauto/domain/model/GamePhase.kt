package ir.amirroid.mafiauto.domain.model

sealed interface GamePhase {
    data object Day : GamePhase
    data object Voting : GamePhase
    data class Defending(val defenders: List<PlayerWithRole>) : GamePhase
    data class LastCard(val player: PlayerWithRole) : GamePhase
    data class Fate(val targetPlayer: PlayerWithRole) : GamePhase
    data object Night : GamePhase
    data object Result : GamePhase
}