package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.core.AppTheme

@Composable
fun ScreenContent(
    content: @Composable () -> Unit
) {
    MSurface(
        color = AppTheme.colorScheme.background,
        contentColor = AppTheme.colorScheme.onBackground,
        content = content,
        modifier = Modifier.fillMaxSize()
    )
}