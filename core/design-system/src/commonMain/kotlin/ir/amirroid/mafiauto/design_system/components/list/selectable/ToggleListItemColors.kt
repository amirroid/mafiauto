package ir.amirroid.mafiauto.design_system.components.list.selectable

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ToggleListItemColors(
    val strokeColor: Color,
    val contentColor: Color,
    val containerColor: Color = Color.Transparent,
    val toggleContainerColor: Color,
    val toggleContentColor: Color,
    val toggleStrokeColor: Color,
    val disabledStrokeColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
)