package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.locales.LocalContentColor
import org.jetbrains.compose.resources.painterResource

@Composable
fun PinIcon(
    isPinned: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    MIcon(
        painter = painterResource(
            if (isPinned) Resources.drawable.pinFilled else Resources.drawable.pinOutlined
        ),
        contentDescription = null,
        modifier = modifier.rotate(45f),
        tint = tint
    )
}