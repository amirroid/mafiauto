package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun MSnackBarHost(
    state: SnakeBarControllerState = rememberSnakeBarControllerState(),
    content: @Composable () -> Unit
) {
    val hazeState = rememberHazeState()
    val snacks by state.snacks.collectAsStateWithLifecycle()

    WithSnakeBarControllerState(state) {
        Box(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.hazeSource(hazeState)) { content.invoke() }
            snacks.forEach { snackBar ->
                key(snackBar.key) {
                    var visible by remember {
                        mutableStateOf(false)
                    }
                    LaunchedEffect(snackBar.visible) {
                        visible = snackBar.visible
                    }
                    AnimatedVisibility(
                        visible,
                        enter = slideInVertically { -it },
                        exit = slideOutVertically { -it }
                    ) {
                        MSnakeBar(snackBar.text, hazeState)
                    }
                }
            }
        }
    }
}