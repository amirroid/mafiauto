package ir.amirroid.mafiauto.design_system.components.popup

import androidx.compose.runtime.Composable

@Composable
internal expect fun PlatformPopup(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable () -> Unit
)