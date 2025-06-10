package ir.amirroid.mafiauto.intro.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.domain.model.Player

@Immutable
data class LobbyScreenState(
    val players: List<Player> = emptyList(),
    val selectedPlayers: List<Player> = emptyList(),
    val newPlayerName: String = ""
)