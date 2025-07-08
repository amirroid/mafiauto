package ir.amirroid.mafiauto.theme.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Immutable
data class Typography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val body: TextStyle,
    val caption: TextStyle,
    val button: TextStyle
)

fun Typography.copyFontFamily(
    newFontFamily: FontFamily
) = copy(
    h1 = h1.copy(fontFamily = newFontFamily),
    h2 = h2.copy(fontFamily = newFontFamily),
    h3 = h3.copy(fontFamily = newFontFamily),
    h4 = h4.copy(fontFamily = newFontFamily),
    body = body.copy(fontFamily = newFontFamily),
    caption = caption.copy(fontFamily = newFontFamily),
    button = button.copy(fontFamily = newFontFamily)
)

val LocalTypography = staticCompositionLocalOf<Typography> { error("Typography not provided") }

