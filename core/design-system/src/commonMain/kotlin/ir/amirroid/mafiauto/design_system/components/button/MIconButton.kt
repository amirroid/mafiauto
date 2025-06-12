package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun MIconButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val alpha by animateFloatAsState(
        if (enabled) 1f else .7f
    )
    val minSize = 36.dp
    Box(
        Modifier
            .sizeIn(minHeight = minSize, minWidth = minSize)
            .clickable(onClick = onClick, enabled = enabled)
            .alpha(alpha),
        content = content,
        contentAlignment = Alignment.Center
    )
}