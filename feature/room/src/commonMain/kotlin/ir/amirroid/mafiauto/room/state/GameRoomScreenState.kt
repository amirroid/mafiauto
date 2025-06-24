package ir.amirroid.mafiauto.room.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.last_card.LastCardUiModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel

@Immutable
data class GameRoomScreenState(
    val players: List<PlayerWithRoleUiModel> = emptyList(),
    val statusChecksCount: Int = 0,
    val pickedPlayerToShowRole: PlayerWithRoleUiModel? = null,
    val showStatus: Boolean = false,
    val currentPhase: GamePhaseUiModel = GamePhaseUiModel.Day,
    val lastCards: List<LastCardUiModel> = emptyList(),
    val currentDay: Int = 0,
    val currentTurn: Int = 0
)