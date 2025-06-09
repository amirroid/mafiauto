package ir.amirroid.mafiauto

import androidx.compose.ui.window.ComposeUIViewController
import ir.amirroid.mafiauto.di.DependencyInjectionConfiguration

fun MainViewController() = ComposeUIViewController(
    configure = {
        DependencyInjectionConfiguration.configure()
    }
) { App() }