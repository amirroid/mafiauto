package ir.amirroid.mafiauto.theme.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.theme.core.LocalHazeState

@Composable
fun WithHazeContent(content: @Composable () -> Unit) {
    val state = rememberHazeState()
    CompositionLocalProvider(
        LocalHazeState provides state
    ) {
        Box(Modifier.Companion.hazeSource(state)) { content.invoke() }
    }
}