package ir.amirroid.mafiauto

import androidx.compose.runtime.Composable
import ir.amirroid.mafiauto.design_system.components.snakebar.MSnackBarHost
import ir.amirroid.mafiauto.design_system.theme.MafiautoTheme
import ir.amirroid.mafiauto.navigation.MainNavigation

@Composable
fun App() {
    MafiautoTheme {
        MSnackBarHost {
            MainNavigation()
        }
    }
}