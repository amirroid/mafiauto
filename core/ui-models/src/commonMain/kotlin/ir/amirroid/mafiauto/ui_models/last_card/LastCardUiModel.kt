package ir.amirroid.mafiauto.ui_models.last_card

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource

@Immutable
data class LastCardUiModel(
    val name: StringResource,
    val explanation: StringResource,
    val key: String,
    val targetCount: Int
)