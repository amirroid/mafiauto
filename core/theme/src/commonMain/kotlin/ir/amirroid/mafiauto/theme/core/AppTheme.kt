package ir.amirroid.mafiauto.theme.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

object AppTheme {
    val horizontalPadding = 24.dp
    val verticalPadding = 16.dp

    val colorScheme: ColorScheme
        @Composable get() = LocalColorScheme.current

    val shapes: Shapes
        @Composable get() = LocalShapes.current

    val typography: Typography
        @Composable get() = LocalTypography.current
}