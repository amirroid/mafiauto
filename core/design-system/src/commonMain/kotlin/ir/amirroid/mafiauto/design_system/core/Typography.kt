package ir.amirroid.mafiauto.design_system.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class Typography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
    val button: TextStyle
)

val LocalTypography = staticCompositionLocalOf<Typography> { error("Typography not provided") }

