package ir.amirroid.mafiauto.design_system.components.popup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider


@Composable
actual fun PlatformPopup(
    onDismissRequest: () -> Unit,
    dismissOnBackPress: Boolean,
    dismissOnClickOutside: Boolean,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            decorFitsSystemWindows = false
        )
    ) {
        (LocalView.current.parent as? DialogWindowProvider)?.window?.let { dialogWindow ->
            SideEffect {
                dialogWindow.navigationBarColor = Color.Transparent.toArgb()
                dialogWindow.statusBarColor = Color.Transparent.toArgb()
                dialogWindow.setWindowAnimations(-1)
                dialogWindow.setDimAmount(0f)
            }
        }
        Box(
            Modifier
                .imePadding()
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            content.invoke()
        }
    }
}