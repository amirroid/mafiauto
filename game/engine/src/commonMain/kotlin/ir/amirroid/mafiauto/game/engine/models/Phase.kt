package ir.amirroid.mafiauto.game.engine.models

sealed interface Phase {
    data object Day : Phase
    data object Voting : Phase
    data class Defending(val defenders: List<Player>) : Phase
    data object Night : Phase
    data object Result : Phase
}