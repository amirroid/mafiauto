package ir.amirroid.mafiauto.design_system.components.slider

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.theme.core.AppTheme
import kotlin.math.roundToInt

@Composable
fun MSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    min: Float = 0f,
    max: Float = 1f,
    steps: Int = 0,
    modifier: Modifier = Modifier,
    thumbSize: Dp = 10.dp,
    sliderSize: Dp = 20.dp
) {
    val layoutDirection = LocalLayoutDirection.current
    BoxWithConstraints(
        modifier = modifier.border(
            width = 1.dp, shape = AppTheme.shapes.medium, color = AppTheme.colorScheme.primary
        ).clip(AppTheme.shapes.medium).height(sliderSize).pointerInput(steps, min, max) {
            detectDragGestures(
                onDrag = { change, _ ->
                    var positionX = change.position.x.coerceIn(0f, size.width.toFloat())
                    if (layoutDirection == LayoutDirection.Rtl) {
                        positionX = (size.width - positionX).coerceIn(0f, size.width.toFloat())
                    }
                    val rawValue = min + (positionX / size.width) * (max - min)
                    val newValue = snapToStep(rawValue, min, max, steps)
                    onValueChange(newValue)
                },
                onDragEnd = { onValueChangeFinished() },
                onDragCancel = { onValueChangeFinished() })
        }, contentAlignment = Alignment.CenterStart
    ) {
        val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)
        val animatedFraction by animateFloatAsState(fraction, label = "")

        Box(
            Modifier.fillMaxHeight().width((maxWidth * animatedFraction).coerceAtLeast(sliderSize))
                .background(AppTheme.colorScheme.primary)
        )

        if (steps > 0) {
            val currentStepIndex = ((value - min) / (max - min) * (steps - 1)).roundToInt()
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(steps) { index ->
                    val color = when {
                        index < currentStepIndex -> AppTheme.colorScheme.onPrimary
                        else -> AppTheme.colorScheme.primary.copy(alpha = 0.6f)
                    }
                    Box(
                        Modifier.width(1.dp).fillMaxHeight(.7f).background(color)
                    )
                }
            }
        }

        Box(Modifier.offset {
            IntOffset(
                (constraints.maxWidth * animatedFraction).coerceAtLeast(sliderSize.toPx())
                    .toInt() - (sliderSize - thumbSize).div(2).toPx()
                    .roundToInt() - thumbSize.toPx().roundToInt(), 0
            )
        }.size(thumbSize).background(AppTheme.colorScheme.onPrimary, CircleShape))
    }
}

private fun snapToStep(value: Float, min: Float, max: Float, steps: Int): Float {
    if (steps <= 0) return value
    val stepSize = (max - min) / (steps - 1)
    val stepIndex = ((value - min) / stepSize).roundToInt()
    return (min + stepIndex * stepSize).coerceIn(min, max)
}
