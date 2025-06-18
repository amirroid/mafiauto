package ir.amirroid.mafiauto.common.compose.components.swipe_to_dismiss

import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

enum class SwipeToDismissBoxValue {
    EndToStart,
    Settled,
    StartToEnd,
}

@Composable
fun SwipeToDismissBox(
    confirmValueChange: (SwipeToDismissBoxValue) -> Boolean,
    positionalThreshold: (Float) -> Float,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable BoxScope.() -> Unit
) {
    var fullWidth by remember { mutableFloatStateOf(0f) }
    val state = remember(fullWidth) {
        val threshold = positionalThreshold(fullWidth)
        AnchoredDraggableState(
            initialValue = SwipeToDismissBoxValue.Settled,
            anchors = DraggableAnchors {
                SwipeToDismissBoxValue.Settled at 0f
                SwipeToDismissBoxValue.EndToStart at -threshold
                SwipeToDismissBoxValue.StartToEnd at threshold
            },
        )
    }

    LaunchedEffect(state.settledValue) {
        confirmValueChange.invoke(state.settledValue)
    }
    Box(
        modifier = modifier
            .anchoredDraggable(
                state = state,
                orientation = orientation,
            )
            .offset {
                IntOffset(x = state.requireOffset().roundToInt(), y = 0)
            }
            .alpha(state.progress(state.targetValue, state.settledValue))
            .onSizeChanged { fullWidth = it.width.toFloat() },
        content = content
    )
}

