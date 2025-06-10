package ir.amirroid.mafiauto.design_system.components.field

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.core.AppTheme

object TextFieldsDefaults {
    val minHeight = 48.dp
    val defaultBorderWidth = 1.5.dp

    private val horizontalPadding = 24.dp
    private val verticalPadding = 8.dp

    val filledTextFieldColors: TextFieldColors
        @Composable get() = AppTheme.colorScheme.let {
            TextFieldColors(it.primary, it.onPrimary, Color.Transparent)
        }

    val outlinedTextFieldColors: TextFieldColors
        @Composable get() = AppTheme.colorScheme.let {
            TextFieldColors(Color.Transparent, it.onBackground, it.primary)
        }

    val defaultShape: Shape
        @Composable get() = AppTheme.shapes.medium

    val defaultContentPadding = PaddingValues(
        start = horizontalPadding,
        top = verticalPadding,
        end = horizontalPadding,
        bottom = verticalPadding
    )
}