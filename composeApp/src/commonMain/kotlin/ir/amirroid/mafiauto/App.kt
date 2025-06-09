package ir.amirroid.mafiauto

import androidx.compose.runtime.Composable
import ir.amirroid.mafiauto.design_system.components.MSurface
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.theme.MafiautoTheme
import ir.amirroid.mafiauto.navigation.MainNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MafiautoTheme {
        MSurface(
            color = AppTheme.colorScheme.background,
            contentColor = AppTheme.colorScheme.onBackground
        ) {
            MainNavigation()
        }
    }
}