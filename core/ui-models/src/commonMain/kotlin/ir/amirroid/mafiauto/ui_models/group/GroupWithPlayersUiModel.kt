package ir.amirroid.mafiauto.ui_models.group

import androidx.compose.runtime.Immutable
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel

@Immutable
data class GroupWithPlayersUiModel(
    val groupId: Long,
    val groupName: String,
    val players: List<PlayerUiModel>
)