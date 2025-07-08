package ir.amirroid.mafiauto.theme.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ir.amirroid.mafiauto.theme.core.PressScaleIndication
import ir.amirroid.mafiauto.theme.core.LocalColorScheme
import ir.amirroid.mafiauto.theme.core.LocalShapes
import ir.amirroid.mafiauto.theme.core.LocalTypography
import ir.amirroid.mafiauto.theme.core.copyFontFamily
import ir.amirroid.mafiauto.theme.locales.LocalContentColor
import ir.amirroid.mafiauto.theme.locales.LocalTextStyle
import ir.amirroid.mafiauto.theme.utils.WithHazeContent

@Composable
fun MafiautoTheme(
    theme: AppThemeUiModel?,
    content: @Composable () -> Unit
) {
    val direction = LocalLayoutDirection.current
    val fonts = if (direction == LayoutDirection.Rtl) VazirFontFamily else SFFontFamily
    val typography = AppTypography.copyFontFamily(fonts)
    val colorScheme = theme?.scheme ?: NeonRedColorScheme
    WithHazeContent {
        CompositionLocalProvider(
            LocalColorScheme provides colorScheme,
            LocalIndication provides PressScaleIndication,
            LocalShapes provides AppShapes,
            LocalTypography provides typography,
            LocalTextStyle provides typography.body,
            LocalContentColor provides colorScheme.onBackground,
            content = content
        )
    }
}