package ir.amirroid.mafiauto.common.compose.components.swipe_to_dismiss

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import ir.amirroid.mafiauto.common.app.utils.getCurrentPlatform
import ir.amirroid.mafiauto.common.app.utils.isDesktop
import kotlinx.coroutines.launch
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
    if (getCurrentPlatform().isDesktop) {
        SwipeToDismissBoxScrollImpl(
            confirmValueChange, positionalThreshold, modifier, orientation, content
        )
    } else {
        SwipeToDismissBoxDragImpl(
            confirmValueChange, positionalThreshold, modifier, orientation, content
        )
    }
}


@Composable
private fun SwipeToDismissBoxScrollImpl(
    confirmValueChange: (SwipeToDismissBoxValue) -> Boolean,
    positionalThreshold: (Float) -> Float,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    content: @Composable BoxScope.() -> Unit
) {
    var offset by remember { mutableFloatStateOf(0f) }
    var fullSize by remember { mutableStateOf(IntSize.Zero) }

    val threshold = positionalThreshold(
        if (orientation == Orientation.Horizontal) fullSize.width.toFloat()
        else fullSize.height.toFloat()
    )

    val coroutineScope = rememberCoroutineScope()
    fun launchSnapTo(target: Float) {
        coroutineScope.launch {
            animate(
                initialValue = offset,
                targetValue = target,
                animationSpec = tween(300)
            ) { value, _ -> offset = value }
        }
    }

    val scrollState = rememberScrollableState { delta ->
        offset += delta
        delta
    }

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress) return@LaunchedEffect
        when {
            offset < -threshold -> {
                val accepted = confirmValueChange(SwipeToDismissBoxValue.EndToStart)
                if (accepted) launchSnapTo(-threshold)
                else launchSnapTo(0f)
            }

            offset > threshold -> {
                val accepted = confirmValueChange(SwipeToDismissBoxValue.StartToEnd)
                if (accepted) launchSnapTo(threshold)
                else launchSnapTo(0f)
            }

            else -> launchSnapTo(0f)
        }
    }


    Box(
        modifier = modifier
            .onSizeChanged { fullSize = it }
            .scrollable(
                state = scrollState,
                orientation = orientation,
                reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl
            )
            .offset {
                if (orientation == Orientation.Horizontal) IntOffset(offset.roundToInt(), 0)
                else IntOffset(0, offset.roundToInt())
            },
        content = content
    )
}

@Composable
private fun SwipeToDismissBoxDragImpl(
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