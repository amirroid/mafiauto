package ir.amirroid.mafiauto.design_system.components.appbar

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor
import ir.amirroid.mafiauto.design_system.locales.LocalTextStyle
import kotlin.math.roundToInt

fun calculateTargetOffset(
    easedProgress: Float,
    navigationIconRect: Rect?,
    textRect: Rect?,
    density: Density,
    direction: LayoutDirection,
): IntOffset {
    if (navigationIconRect == null || textRect == null) return IntOffset.Zero

    val spacing = with(density) { 8.dp.toPx() }.roundToInt()
    val collapsedOffsetX = when (direction) {
        LayoutDirection.Ltr -> {
            (navigationIconRect.right - textRect.left + spacing)
        }

        LayoutDirection.Rtl -> {
            (textRect.right - navigationIconRect.left + spacing)
        }

        else -> 0f
    }

    val calculatedX = lerp(collapsedOffsetX, 0f, easedProgress)
    return IntOffset(
        x = calculatedX.roundToInt(),
        y = 0
    )
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun CollapsingTopAppBarScaffold(
    title: @Composable () -> Unit,
    state: CollapsingAppBarState = rememberCollapsingAppBarState(),
    navigationIcon: (@Composable () -> Unit)? = null,
    hazeState: HazeState = rememberHazeState(),
    hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface),
    content: @Composable (PaddingValues) -> Unit
) {
    val density = LocalDensity.current
    val direction = LocalLayoutDirection.current

    var navigationIconRect by remember { mutableStateOf<Rect?>(null) }
    var textRect by remember { mutableStateOf<Rect?>(null) }

    val paddingValues = remember(state.maxHeightPx) {
        PaddingValues(top = with(density) { state.maxHeightPx.toDp() })
    }

    val easedProgress = LinearOutSlowInEasing.transform(state.progress)

    val appBarHeight = with(density) { state.heightPx.toDp() }
    val fontSize = lerp(16f, 28f, easedProgress).sp
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }

    val nestedScrollConnection = remember(state) {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                state.update(consumed.y)
                return Offset.Zero
            }
        }
    }

    val targetOffsetPx = remember(easedProgress, navigationIconRect, textRect) {
        calculateTargetOffset(
            easedProgress,
            navigationIconRect,
            textRect,
            density,
            direction,
        )
    }

    val animatedOffset by animateIntOffsetAsState(targetOffsetPx)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .nestedScroll(nestedScrollConnection)
    ) {
        Box(Modifier.hazeSource(hazeState)) {
            content(paddingValues)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(appBarHeight + statusBarHeight)
                .hazeEffect(hazeState, style = hazeStyle)
                .padding(16.dp)
        ) {
            navigationIcon?.let { iconContent ->
                Box(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(top = statusBarHeight)
                        .onGloballyPositioned {
                            navigationIconRect = it.boundsInWindow()
                        }
                ) {
                    iconContent()
                }
            }

            Box(
                Modifier
                    .align(Alignment.BottomStart)
                    .onGloballyPositioned {
                        textRect = it.boundsInWindow()
                    }
                    .offset { animatedOffset }
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides AppTheme.typography.h1.copy(fontSize = fontSize),
                    LocalContentColor provides AppTheme.colorScheme.onSurface
                ) {
                    title()
                }
            }
        }
    }
}