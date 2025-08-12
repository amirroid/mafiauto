package ir.amirroid.mafiauto.settings.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.farsitel.bazaar.updater.BazaarUpdater
import ir.amirroid.mafiauto.ui_models.update.UpdateInfoUiModel

actual fun getPlatformUpdaterDialog(info: UpdateInfoUiModel): @Composable (() -> Unit)? {
    return {
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            BazaarUpdater.updateApplication(context)
        }
    }
}