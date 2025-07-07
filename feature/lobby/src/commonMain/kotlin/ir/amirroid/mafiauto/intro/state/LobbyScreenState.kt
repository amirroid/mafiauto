package ir.amirroid.mafiauto.intro.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel

@Immutable
data class LobbyScreenState(
    val players: List<PlayerUiModel> = emptyList(),
    val selectedPlayers: List<PlayerUiModel> = emptyList(),
    val newPlayerName: String = "",
    val isEditing: Boolean = false
)