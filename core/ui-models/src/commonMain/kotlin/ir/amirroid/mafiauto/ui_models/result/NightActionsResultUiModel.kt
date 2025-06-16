package ir.amirroid.mafiauto.ui_models.result

import androidx.compose.runtime.Immutable

@Immutable
data class NightActionsResultUiModel(
    val deathCount: Int,
    val revivedCount: Int
)