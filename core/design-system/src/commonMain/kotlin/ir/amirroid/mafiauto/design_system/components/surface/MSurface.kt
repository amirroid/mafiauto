package ir.amirroid.mafiauto.design_system.components.surface

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalContentColor

@Composable
fun MSurface(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClickWhenDisabled: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.medium,
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
                .combinedClickable(
                    enabled = enabled,
                    onClick = onClick,
                    onDoubleClick = onDoubleClick,
                    onLongClick = onLongClick,
                    interactionSource = interactionSource,
                    indication = LocalIndication.current
                )
                .focusable()
                .then(
                    if (onClickWhenDisabled != null && !enabled)
                        Modifier.clickable(
                            onClick = onClickWhenDisabled,
                            interactionSource = interactionSource,
                            indication = null
                        )
                    else Modifier
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
    shape: Shape = AppTheme.shapes.medium,
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