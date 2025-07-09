package ir.amirroid.mafiauto.design_system.components.sheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.theme.core.AppTheme

@Composable
fun MBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    BaseBlurredPopup(
        isVisible = visible,
        onDismissRequest = onDismissRequest,
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            MSurface(
                border = BorderStroke(1.5.dp, AppTheme.colorScheme.primary),
                color = AppTheme.colorScheme.background,
                contentColor = AppTheme.colorScheme.onBackground,
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp)
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(.83f)
                    .pointerInput(Unit) { detectTapGestures() },
                shape = AppTheme.shapes.large
            ) {
                Box(Modifier.padding(20.dp)) {
                    content.invoke()
                }
            }
        }
    }
}