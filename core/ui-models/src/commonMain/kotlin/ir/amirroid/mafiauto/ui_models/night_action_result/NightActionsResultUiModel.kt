package ir.amirroid.mafiauto.ui_models.night_action_result

import androidx.compose.runtime.Immutable

@Immutable
data class NightActionsResultUiModel(
    val deathCount: Int,
    val revivedCount: Int
)