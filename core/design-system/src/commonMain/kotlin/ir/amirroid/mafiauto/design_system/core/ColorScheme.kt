package ir.amirroid.mafiauto.design_system.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val disabled: Color,
    val onDisabled: Color,
)


val LocalColorScheme = staticCompositionLocalOf<ColorScheme> { error("ColorSchema not provided") }