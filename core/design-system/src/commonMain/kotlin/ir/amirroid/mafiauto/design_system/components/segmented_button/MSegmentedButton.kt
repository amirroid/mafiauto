package ir.amirroid.mafiauto.design_system.components.segmented_button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import ir.amirroid.mafiauto.design_system.components.button.ButtonDefaults
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalContentColor

@Composable
fun <T> MSegmentedButton(
    items: List<T>,
    selectedItem: T,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.defaultContentPadding,
    shape: Shape = AppTheme.shapes.large,
    borderWidth: Dp = ButtonDefaults.defaultBorderWidth,
    content: @Composable (T) -> Unit
) {
    Row(
        modifier = modifier
            .border(
                borderWidth,
                AppTheme.colorScheme.primary,
                shape = shape
            )
            .clip(shape)
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedItem == item
            val color = if (selected) AppTheme.colorScheme.primary else Color.Transparent
            val contentColor =
                if (selected) AppTheme.colorScheme.onPrimary else LocalContentColor.current
            MSurface(
                modifier = Modifier.weight(1f),
                color = color,
                contentColor = contentColor,
                onClick = { onClick.invoke(item) }
            ) {
                Box(
                    Modifier.fillMaxWidth()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    content.invoke(item)
                }
            }
            if (index != items.size - 1) {
                Box(
                    Modifier.fillMaxHeight().width(borderWidth)
                        .background(AppTheme.colorScheme.primary)
                )
            }
        }
    }
}