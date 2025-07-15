package ir.amirroid.mafiauto.ui_models.group_with_player

import androidx.compose.runtime.Immutable

@Immutable
data class GroupWithPlayersUiModel(
    val groupId: Long,
    val groupName: String,
    val formatedPlayersList: String,
    val isPinnedGroup: Boolean
)