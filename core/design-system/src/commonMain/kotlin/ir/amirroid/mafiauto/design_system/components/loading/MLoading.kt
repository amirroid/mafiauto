package ir.amirroid.mafiauto.design_system.components.loading

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.theme.core.AppTheme
import kotlinx.coroutines.delay

@Composable
fun MLoading(
    size: Dp = 48.dp,
    shape: Shape = AppTheme.shapes.medium,
    color: Color = AppTheme.colorScheme.primary,
    eachAnimationTime: Int = 500,
    delayBetweenAnimations: Int = 300,
) {
    var animateX by remember { mutableStateOf(true) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(animateX) {
        rotation.snapTo(0f)
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = tween(durationMillis = eachAnimationTime, easing = LinearEasing)
        )
        delay(delayBetweenAnimations.toLong())
        animateX = !animateX
    }

    MSurface(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                rotationX = if (animateX) rotation.value else 0f
                rotationY = if (animateX) 0f else rotation.value
                cameraDistance = 8 * density
            }
            .clip(shape),
        shape = shape,
        color = color
    ) {}
}