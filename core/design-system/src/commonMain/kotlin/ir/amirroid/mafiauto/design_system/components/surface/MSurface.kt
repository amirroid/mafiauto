package ir.amirroid.mafiauto.design_system.components.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor

@Composable
fun MSurface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colorScheme.surface,
    contentColor: Color = AppTheme.colorScheme.onSurface,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier =
            modifier
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = LocalIndication.current
                )
                .then(
                    if (border == null) Modifier else Modifier.border(border, shape = shape)
                )
                .clip(shape)
                .background(color),
        propagateMinConstraints = true,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor
        ) {
            content()
        }
    }
}

@Composable
fun MSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = AppTheme.colorScheme.surface,
    contentColor: Color = AppTheme.colorScheme.onSurface,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier =
            modifier
                .then(
                    if (border == null) Modifier else Modifier.border(border, shape = shape)
                )
                .clip(shape)
                .background(color),
        propagateMinConstraints = true
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
        ) {
            content()
        }
    }
}