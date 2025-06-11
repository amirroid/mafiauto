package ir.amirroid.mafiauto.design_system.locales

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

val LocalHazeState = compositionLocalOf<HazeState> { error("HazeState not provided") }

@Composable
fun WithHazeContent(content: @Composable () -> Unit) {
    val state = rememberHazeState()
    CompositionLocalProvider(
        LocalHazeState provides state
    ) {
        Box(Modifier.hazeSource(state)) { content.invoke() }
    }
}