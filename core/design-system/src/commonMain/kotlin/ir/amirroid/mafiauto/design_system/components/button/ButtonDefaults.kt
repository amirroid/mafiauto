package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.theme.core.AppTheme

object ButtonDefaults {
    private val buttonHorizontalPadding = 24.dp
    private val buttonVerticalPadding = 8.dp

    val defaultBorderWidth = 1.5.dp
    val minHeight = 40.dp

    val fabSize = DpSize(56.dp, 56.dp)

    val primaryButtonColors: ButtonColors
        @Composable get() = AppTheme.colorScheme.let {
            ButtonColors(it.primary, it.onPrimary, it.disabled, it.onDisabled)
        }

    val outlinedButtonColors: ButtonColors
        @Composable get() = AppTheme.colorScheme.let {
            ButtonColors(it.primary, it.primary, it.disabled, it.onDisabled)
        }

    val defaultShape: Shape
        @Composable get() = AppTheme.shapes.medium

    val defaultContentPadding = PaddingValues(
        start = buttonHorizontalPadding,
        top = buttonVerticalPadding,
        end = buttonHorizontalPadding,
        bottom = buttonVerticalPadding
    )
}