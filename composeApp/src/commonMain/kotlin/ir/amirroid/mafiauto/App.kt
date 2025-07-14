package ir.amirroid.mafiauto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.compose.components.ScreenContent
import ir.amirroid.mafiauto.common.compose.locales.LocalLocalization
import ir.amirroid.mafiauto.common.compose.utils.LocaleUtils
import ir.amirroid.mafiauto.design_system.components.loading.MLoading
import ir.amirroid.mafiauto.design_system.components.snakebar.MSnackBarHost
import ir.amirroid.mafiauto.navigation.MainNavigation
import ir.amirroid.mafiauto.theme.theme.MafiautoTheme
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = koinViewModel()
    val configuration by viewModel.settingsConfiguration.collectAsStateWithLifecycle()

    if (configuration == null) {
        LoadingContent()
    } else {
        HandleAppLanguage(configuration) {
            MafiautoTheme(theme = configuration!!.theme) {
                MSnackBarHost {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
private fun HandleAppLanguage(configuration: SettingsUiModel?, content: @Composable () -> Unit) {
    val initialLayoutDirection = LocalLayoutDirection.current
    var currentLayoutDirection by remember { mutableStateOf(initialLayoutDirection) }
    var isLocaleSet by remember { mutableStateOf(false) }
    var currentLanguage by remember { mutableStateOf(configuration?.languageCode ?: "en") }

    LaunchedEffect(configuration) {
        LocaleUtils.setDefaultLanguage(configuration?.languageCode ?: return@LaunchedEffect)
        currentLayoutDirection = configuration.language.layoutDirection
        currentLanguage = configuration.languageCode
        isLocaleSet = true
    }
    if (isLocaleSet) {
        CompositionLocalProvider(
            LocalLayoutDirection provides currentLayoutDirection,
            LocalLocalization provides currentLanguage
        ) {
            content.invoke()
        }
    } else {
        LoadingContent()
    }
}

@Composable
private fun LoadingContent() {
    MafiautoTheme(null) {
        ScreenContent {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                MLoading()
            }
        }
    }
}