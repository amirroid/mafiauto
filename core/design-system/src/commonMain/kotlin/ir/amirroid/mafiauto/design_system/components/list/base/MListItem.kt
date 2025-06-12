package ir.amirroid.mafiauto.design_system.components.list.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults

@Composable
fun MListItem(
    text: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ListItemColors = ListItemDefaults.defaultColors,
    contentPadding: PaddingValues = ListItemDefaults.defaultContentPadding,
    borderWidth: Dp = ListItemDefaults.defaultBorderWidth,
    enabled: Boolean = true,
    shape: Shape = ListItemDefaults.defaultShape
) {
    MSurface(
        onClick = onClick,
        modifier = modifier.heightIn(min = 58.dp),
        contentColor = colors.contentColor,
        color = colors.containerColor,
        border = BorderStroke(borderWidth, colors.strokeColor),
        shape = shape,
        enabled = enabled
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.weight(1f), content = text)
        }
    }
}