package ir.amirroid.mafiauto.design_system.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ir.amirroid.mafiauto.design_system.core.PressScaleIndication
import ir.amirroid.mafiauto.design_system.core.LocalColorScheme
import ir.amirroid.mafiauto.design_system.core.LocalShapes
import ir.amirroid.mafiauto.design_system.core.LocalTypography
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor
import ir.amirroid.mafiauto.design_system.locales.LocalTextStyle

@Composable
fun MafiautoTheme(
    content: @Composable () -> Unit
) {
    val typography = AppTypography
    val colorScheme = NeonRedColorScheme
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