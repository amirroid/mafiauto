package ir.amirroid.mafiauto.theme.core

import androidx.compose.runtime.compositionLocalOf
import dev.chrisbanes.haze.HazeState

val LocalHazeState = compositionLocalOf<HazeState> { error("HazeState not provided") }