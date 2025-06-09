package ir.amirroid.mafiauto.design_system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import ir.amirroid.mafiauto.design_system.core.AppTheme

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
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier =
                modifier
                    .then(
                        if (border == null) Modifier else Modifier.border(border, shape = shape)
                    )
                    .clickable(
                        enabled = enabled,
                        onClick = onClick,
                        interactionSource = interactionSource,
                        indication = LocalIndication.current
                    )

                    .minimumInteractiveComponentSize()
                    .clip(shape)
                    .background(color),
            propagateMinConstraints = true,
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
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier =
                modifier
                    .minimumInteractiveComponentSize()
                    .clip(shape)
                    .background(color),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}