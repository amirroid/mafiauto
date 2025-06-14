package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformBackHandler(handle: () -> Unit)