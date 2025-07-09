package ir.amirroid.mafiauto.theme.theme

import androidx.compose.ui.graphics.Color
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.ColorScheme
import org.jetbrains.compose.resources.StringResource

internal val NeonRedColorScheme = ColorScheme(
    primary = Color(0xFFFF1744),
    onPrimary = Color(0xFF2F000A),
    secondary = Color(0xFFFF5252),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFF140B0E),
    onBackground = Color.White,
    surface = Color(0xFF201014),
    onSurface = Color(0xFFE0E0E0),
    disabled = Color(0xFF4A2A2A),
    onDisabled = Color(0xFFBCAAA4)
)

internal val NeonBlueColorScheme = ColorScheme(
    primary = Color(0xFF448AFF),
    onPrimary = Color(0xFF001E3C),
    secondary = Color(0xFF82B1FF),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF140B0E),
    onBackground = Color.White,
    surface = Color(0xFF101A24),
    onSurface = Color(0xFFB3E5FC),
    disabled = Color(0xFF2A3B47),
    onDisabled = Color(0xFF90A4AE)
)

internal val NeonGreenColorScheme = ColorScheme(
    primary = Color(0xFF00E676),
    onPrimary = Color(0xFF00391E),
    secondary = Color(0xFF69F0AE),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF140B0E),
    onBackground = Color.White,
    surface = Color(0xFF142019),
    onSurface = Color(0xFFC8E6C9),
    disabled = Color(0xFF2E3B32),
    onDisabled = Color(0xFFA5D6A7)
)


val DefaultColorScheme = NeonRedColorScheme

enum class AppThemeUiModel(
    val displayName: StringResource,
    val scheme: ColorScheme
) {
    RED(Resources.strings.red, NeonRedColorScheme),
    GREEN(Resources.strings.green, NeonGreenColorScheme),
    BLUE(Resources.strings.blue, NeonBlueColorScheme)
}