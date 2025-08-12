package ir.amirroid.mafiauto.settings.components

import androidx.compose.runtime.Composable
import ir.amirroid.mafiauto.ui_models.update.UpdateInfoUiModel

expect fun getPlatformUpdaterDialog(info: UpdateInfoUiModel): (@Composable () -> Unit)?