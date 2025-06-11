package ir.amirroid.mafiauto.room.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel

@Immutable
data class GameRoomScreenState(
    val players: List<PlayerWithRoleUiModel>,
    val statusChecksCount: Int = 0,
    val pickedPlayerToShowRole: PlayerWithRoleUiModel? = null
)