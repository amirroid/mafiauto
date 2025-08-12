package ir.amirroid.mafiauto.settings.components

import androidx.compose.runtime.Composable
import ir.amirroid.mafiauto.ui_models.update.UpdateInfoUiModel

actual fun getPlatformUpdaterDialog(info: UpdateInfoUiModel): (@Composable () -> Unit)? = null