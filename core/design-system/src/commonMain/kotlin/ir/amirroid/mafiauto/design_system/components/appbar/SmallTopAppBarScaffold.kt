package ir.amirroid.mafiauto.design_system.components.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun SmallTopAppBarScaffold(
    title: @Composable () -> Unit,
    navigationIcon: (@Composable () -> Unit)? = null,
    hazeState: HazeState = rememberHazeState(),
    hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface),
    height: Dp = 64.dp,
    content: @Composable (PaddingValues) -> Unit
) {
    val density = LocalDensity.current
    val statusBarHeight = with(density) { WindowInsets.statusBars.getTop(density).toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        Box(Modifier.hazeSource(hazeState)) {
            content(PaddingValues(top = height))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height + statusBarHeight)
                .hazeEffect(hazeState, style = hazeStyle)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            navigationIcon?.let { iconContent ->
                Box(
                    Modifier.statusBarsPadding()
                ) {
                    iconContent()
                }
            }

            Box(
                Modifier.statusBarsPadding()
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides AppTheme.typography.h3,
                    LocalContentColor provides AppTheme.colorScheme.onSurface
                ) {
                    title()
                }
            }
        }
    }
}