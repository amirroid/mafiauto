package ir.amirroid.mafiauto.design_system.components.list.selectable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.ListItemColors
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem

@Composable
fun MToggleListItem(
    selected: Boolean,
    onClick: (Boolean) -> Unit,
    onLongClick: (() -> Unit)? = null,
    text: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    colors: ToggleListItemColors = ListItemDefaults.defaultToggleColors,
    contentPadding: PaddingValues = ListItemDefaults.defaultContentPadding,
    borderWidth: Dp = ListItemDefaults.defaultBorderWidth,
    enabled: Boolean = true,
    shape: Shape = ListItemDefaults.defaultShape
) {
    val strokeColor by animateColorAsState(
        when {
            !enabled -> colors.disabledStrokeColor
            selected -> colors.toggleStrokeColor
            else -> colors.strokeColor
        }
    )
    val contentColor by animateColorAsState(
        when {
            !enabled -> colors.disabledContentColor
            selected -> colors.toggleContentColor
            else -> colors.contentColor
        }
    )
    val containerColor by animateColorAsState(
        when {
            !enabled -> colors.disabledContainerColor
            selected -> colors.toggleContainerColor
            else -> colors.containerColor
        }
    )
    MListItem(
        text = text,
        onClick = { onClick.invoke(selected.not()) },
        modifier = modifier,
        contentPadding = contentPadding,
        borderWidth = borderWidth,
        shape = shape,
        enabled = enabled,
        colors = ListItemColors(
            strokeColor = strokeColor,
            contentColor = contentColor,
            containerColor = containerColor
        ),
        onLongClick = onLongClick
    )
}