package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import ir.amirroid.mafiauto.design_system.components.MSurface
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalTextStyle

@Composable
fun MButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    MSurface(
        onClick = onClick,
        modifier = modifier
            .heightIn(min = ButtonDefaults.minHeight)
            .semantics { role = Role.Button },
        shape = shape,
        contentColor = colors.contentColor,
        color = colors.containerColor,
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