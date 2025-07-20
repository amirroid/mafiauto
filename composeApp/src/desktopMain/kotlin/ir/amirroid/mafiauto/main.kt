package ir.amirroid.mafiauto

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ir.amirroid.mafiauto.di.DependencyInjectionConfiguration
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import java.awt.Dimension
import java.awt.Toolkit
import kotlin.math.roundToInt

fun main() {
    DependencyInjectionConfiguration.configure { modules(projectModule) }


    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenHeight = screenSize.height
    val windowHeightPx = (screenHeight * 0.8).toInt()
    val windowWidthPx = (windowHeightPx * 9) / 16

    val windowSize = DpSize(
        width = windowWidthPx.dp, height = windowHeightPx.dp
    )
    application {
        Window(
            onCloseRequest = ::exitApplication, state = rememberWindowState(size = windowSize),
            title = stringResource(Resources.strings.appName),
            icon = painterResource(Resources.drawable.logoRed)
        ) {
            LaunchedEffect(Unit) {
                window.minimumSize = Dimension(
                    windowSize.width.value.roundToInt(),
                    windowSize.height.value.times(.8f).roundToInt()
                )
            }
            CompositionLocalProvider(LocalDensity provides Density(1.5f)) {
                App()
            }
        }
    }
}