package ir.amirroid.mafiauto.design_system.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.list.base.ListItemColors
import ir.amirroid.mafiauto.design_system.components.list.selectable.ToggleListItemColors
import ir.amirroid.mafiauto.theme.core.AppTheme

object ListItemDefaults {
    val defaultBorderWidth = 1.5.dp

    private val horizontalPadding = 24.dp
    private val verticalPadding = 8.dp

    val defaultColors: ListItemColors
        @Composable get() = AppTheme.colorScheme.let {
            ListItemColors(it.primary, it.primary)
        }

    val filledColors: ListItemColors
        @Composable get() = AppTheme.colorScheme.let {
            ListItemColors(Color.Transparent, it.onPrimary, it.primary)
        }

    val defaultToggleColors: ToggleListItemColors
        @Composable get() = AppTheme.colorScheme.let {
            ToggleListItemColors(
                strokeColor = it.primary,
                contentColor = it.primary,
                toggleContentColor = it.onPrimary,
                toggleStrokeColor = Color.Transparent,
                toggleContainerColor = it.primary,
                disabledContainerColor = it.disabled,
                disabledStrokeColor = it.disabled,
                disabledContentColor = it.onDisabled,
            )
        }

    val defaultContentPadding = PaddingValues(
        start = horizontalPadding,
        top = verticalPadding,
        end = horizontalPadding,
        bottom = verticalPadding
    )

    val defaultShape: Shape
        @Composable get() = AppTheme.shapes.large
}