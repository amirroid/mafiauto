package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalTextStyle

@Composable
fun MButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClickWhenDisabled: (() -> Unit)? = null,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors,
    shape: Shape = ButtonDefaults.defaultShape,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.defaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by animateColorAsState(
        if (enabled) colors.contentColor else colors.disabledContentColor
    )
    val containerColor by animateColorAsState(
        if (enabled) colors.containerColor else colors.disabledContainerColor
    )
    MSurface(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = ButtonDefaults.minHeight)
            .semantics { role = Role.Button },
        shape = shape,
        contentColor = contentColor,
        enabled = enabled,
        color = containerColor,
        onClickWhenDisabled = onClickWhenDisabled
    ) {
        CompositionLocalProvider(LocalTextStyle provides AppTheme.typography.button) {
            Row(
                modifier = Modifier.padding(contentPadding),
                content = content,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
        }
    }
}

@Composable
fun MOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClickWhenDisabled: (() -> Unit)? = null,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors,
    shape: Shape = ButtonDefaults.defaultShape,
    enabled: Boolean = true,
    borderWidth: Dp = ButtonDefaults.defaultBorderWidth,
    contentPadding: PaddingValues = ButtonDefaults.defaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColor by animateColorAsState(
        if (enabled) colors.contentColor else colors.disabledContentColor
    )
    val borderColor by animateColorAsState(
        if (enabled) colors.containerColor else colors.disabledContainerColor
    )
    MSurface(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = ButtonDefaults.minHeight)
            .semantics { role = Role.Button },
        shape = shape,
        contentColor = contentColor,
        enabled = enabled,
        color = Color.Transparent,
        border = BorderStroke(borderWidth, borderColor),
        onClickWhenDisabled = onClickWhenDisabled
    ) {
        CompositionLocalProvider(LocalTextStyle provides AppTheme.typography.button) {
            Row(
                modifier = Modifier.padding(contentPadding),
                content = content,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
        }
    }
}