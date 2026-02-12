package ir.amirroid.mafiauto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import ir.amirroid.mafiauto.common.compose.modifiers.thenIf
import ir.amirroid.mafiauto.di.DependencyInjectionConfiguration
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.events.Event

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    DependencyInjectionConfiguration.configure { modules(projectModule) }

    ComposeViewport(document.body ?: return) {
        ResponsiveBox {
            App()
        }
    }
}


@Composable
fun ResponsiveBox(content: @Composable () -> Unit) {
    var windowWidth by remember { mutableStateOf(window.innerWidth) }
    val isLargeMonitor = windowWidth > 768

    DisposableEffect(Unit) {
        val resizeListener: (Event) -> Unit = {
            windowWidth = window.innerWidth
        }
        window.addEventListener("resize", resizeListener)
        onDispose {
            window.removeEventListener("resize", resizeListener)
        }
    }

    Box(Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center) {
        Box(
            Modifier.thenIf(isLargeMonitor) {
                clip(RoundedCornerShape(8.dp))
                    .fillMaxHeight(.95f)
                    .aspectRatio(9 / 16f)
            }
        ) { content.invoke() }
    }
}