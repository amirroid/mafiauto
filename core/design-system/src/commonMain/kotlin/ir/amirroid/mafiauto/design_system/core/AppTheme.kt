package ir.amirroid.mafiauto.design_system.core

import androidx.compose.runtime.Composable

object AppTheme {
    val colorScheme: ColorScheme
        @Composable get() = LocalColorScheme.current

    val shapes: Shapes
        @Composable get() = LocalShapes.current
}