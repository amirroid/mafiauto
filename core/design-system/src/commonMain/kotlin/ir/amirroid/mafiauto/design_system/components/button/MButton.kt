package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.MSurface

@Composable
fun MButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.primaryButtonColors,
    shape: Shape = ButtonDefaults.defaultShape,
    contentPadding: PaddingValues = ButtonDefaults.defaultContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val borderGradient = Brush.verticalGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.8f),
            Color.Gray.copy(alpha = 0.2f)
        )
    )

    MSurface(
        onClick = onClick,
        modifier = modifier
            .semantics { role = Role.Button },
        shape = shape,
        contentColor = colors.contentColor,
        color = colors.containerColor
    ) {
        Row(
            modifier = Modifier.padding(contentPadding),
            content = content,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
    }
}