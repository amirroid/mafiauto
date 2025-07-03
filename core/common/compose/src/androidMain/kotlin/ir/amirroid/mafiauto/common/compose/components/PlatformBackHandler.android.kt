package ir.amirroid.mafiauto.common.compose.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun PlatformBackHandler(handle: () -> Unit) {
    BackHandler { handle.invoke() }
}