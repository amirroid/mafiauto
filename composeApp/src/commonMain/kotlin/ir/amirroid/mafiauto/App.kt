package ir.amirroid.mafiauto

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.compose.utils.LocaleUtils
import ir.amirroid.mafiauto.design_system.components.snakebar.MSnackBarHost
import ir.amirroid.mafiauto.theme.theme.MafiautoTheme
import ir.amirroid.mafiauto.navigation.MainNavigation
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val configuration by viewModel.settingsConfiguration.collectAsStateWithLifecycle()

    HandleAppLanguage(configuration) {
        MafiautoTheme(theme = configuration?.theme) {
            MSnackBarHost {
                MainNavigation()
            }
        }
    }
}

@Composable
private fun HandleAppLanguage(configuration: SettingsUiModel?, content: @Composable () -> Unit) {
    val initialLayoutDirection = LocalLayoutDirection.current
    var currentLayoutDirection by remember { mutableStateOf(initialLayoutDirection) }

    LaunchedEffect(configuration) {
        LocaleUtils.setDefaultLanguage(configuration?.languageCode ?: return@LaunchedEffect)
        currentLayoutDirection = configuration.language.layoutDirection
    }

    CompositionLocalProvider(
        LocalLayoutDirection provides currentLayoutDirection,
        content = content
    )
}