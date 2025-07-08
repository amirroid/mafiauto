package ir.amirroid.mafiauto.intro.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class LobbyScreenState(
    val players: ImmutableList<PlayerUiModel> = emptyImmutableList(),
    val selectedPlayers: ImmutableList<PlayerUiModel> = emptyImmutableList(),
    val newPlayerName: String = "",
    val isEditing: Boolean = false
)