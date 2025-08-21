package ir.amirroid.mafiauto.design_system.components.popup

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import ir.amirroid.mafiauto.theme.core.LocalHazeState

@Composable
fun BaseBlurredPopup(
    isVisible: Boolean,
    onDismissRequest: (() -> Unit)? = null,
    onExitAnimationStarted: (() -> Unit)? = null,
    dismissOnBackPress: Boolean = true,
    withContentAnimation: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    if (LocalInspectionMode.current) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center, content = content)
    } else {
        val hazeState = LocalHazeState.current
        var visibleAnim by remember(isVisible) { mutableStateOf(isVisible) }
        val percent = remember { Animatable(0f) }
        LaunchedEffect(visibleAnim) {
            if (visibleAnim) {
                percent.animateTo(1f, animationSpec = tween(300))
            } else {
                onExitAnimationStarted?.invoke()
                percent.animateTo(0f, animationSpec = tween(200))
                onDismissRequest?.invoke()
            }
        }
        PlatformPopup(
            onDismissRequest = onDismissRequest?.let {
                {
                    if (dismissOnBackPress) {
                        visibleAnim = false
                    }
                }
            } ?: {},
            dismissOnClickOutside = dismissOnBackPress,
            dismissOnBackPress = dismissOnBackPress,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures {
                            if (!dismissOnBackPress) return@detectTapGestures
                            onDismissRequest?.let { visibleAnim = false }
                        }
                    }
            ) {
                Box(
                    Modifier
                        .alpha(percent.value)
                        .fillMaxSize()
                        .hazeEffect(hazeState) {
                            tints = listOf(HazeTint(Color.Black.copy(.5f)))
                            backgroundColor = Color.Black.copy(.5f)
                            blurRadius = 10.dp
                            blurEnabled = true
                        }
                ) {
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .then(
                            if (withContentAnimation) Modifier
                                .alpha(percent.value)
                                .scale(.6f + .4f * percent.value)
                            else Modifier
                        ),
                    contentAlignment = Alignment.Center,
                    content = content
                )
            }
        }
    }
}
