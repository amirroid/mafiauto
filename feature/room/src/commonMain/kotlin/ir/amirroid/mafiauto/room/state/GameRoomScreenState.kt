package ir.amirroid.mafiauto.room.state

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.ui_models.effect.PlayerEffectUiModel
import ir.amirroid.mafiauto.ui_models.last_card.LastCardUiModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class GameRoomScreenState(
    val players: ImmutableList<PlayerWithRoleUiModel> = emptyImmutableList(),
    val statusChecksCount: Int = 0,
    val pickedPlayerToShowRole: PlayerWithRoleUiModel? = null,
    val showStatus: Boolean = false,
    val currentPhase: GamePhaseUiModel = GamePhaseUiModel.Day,
    val lastCards: ImmutableList<LastCardUiModel> = emptyImmutableList(),
    val currentDay: Int = 0,
    val currentTurn: Int = 0,
    var pickedPlayerToApplyEffect: PlayerWithEffect? = null
)

@Immutable
data class PlayerWithEffect(
    val player: PlayerWithRoleUiModel,
    val effect: PlayerEffectUiModel
)