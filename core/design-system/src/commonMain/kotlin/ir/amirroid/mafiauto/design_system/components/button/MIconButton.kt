package ir.amirroid.mafiauto.design_system.components.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MIconButton(
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val minSize = 36.dp
    Box(
        Modifier.sizeIn(minHeight = minSize, minWidth = minSize).clickable(onClick = onClick),
        content = content,
        contentAlignment = Alignment.Center
    )
}