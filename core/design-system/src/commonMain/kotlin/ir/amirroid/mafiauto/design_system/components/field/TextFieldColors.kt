package ir.amirroid.mafiauto.design_system.components.field

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class TextFieldColors(
    val containerColor: Color,
    val contentColor: Color,
    val borderColor: Color,
    val cursorColor: Color = contentColor,
    val placeholderColor: Color = contentColor.copy(.7f)
)
