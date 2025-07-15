package ir.amirroid.mafiauto.ui_models.group

import androidx.compose.runtime.Immutable

@Immutable
data class GroupUiModel(
    val name: String,
    val isPinned: Boolean
)