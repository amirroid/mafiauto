package ir.amirroid.mafiauto.design_system.components.appbar

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class CollapsingAppBarState(
    val maxHeightPx: Float,
    val minHeightPx: Float
) {
    var heightPx by mutableFloatStateOf(maxHeightPx)

    val progress: Float
        get() = (heightPx - minHeightPx) / (maxHeightPx - minHeightPx)

    fun update(delta: Float) {
        heightPx = (heightPx + delta).coerceIn(minHeightPx, maxHeightPx)
    }
}


@Composable
fun rememberCollapsingAppBarState(
    maxHeight: Dp = 128.dp,
    minHeight: Dp = 56.dp
): CollapsingAppBarState {
    val density = LocalDensity.current
    val maxPx = with(density) { maxHeight.toPx() }
    val minPx = with(density) { minHeight.toPx() }

    return remember(maxPx, minPx) {
        CollapsingAppBarState(maxHeightPx = maxPx, minHeightPx = minPx)
    }
}