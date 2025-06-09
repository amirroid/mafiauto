package ir.amirroid.mafiauto.design_system.components.list.base

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ListItemColors(
    val strokeColor: Color,
    val contentColor: Color,
    val containerColor: Color = Color.Transparent
)