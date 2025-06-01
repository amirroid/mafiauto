package ir.amirroid.mafiauto.design_system.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import ir.amirroid.mafiauto.design_system.core.PressScaleIndication
import ir.amirroid.mafiauto.design_system.core.LocalColorScheme
import ir.amirroid.mafiauto.design_system.core.LocalShapes

@Composable
fun MafiautoTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides NeonBlueColorScheme,
        LocalIndication provides PressScaleIndication,
        LocalShapes provides AppShapes,
        content = content
    )
}