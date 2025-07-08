package ir.amirroid.mafiauto.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.segmented_button.MSegmentedButton
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.settings.viewmodel.SettingsViewModel
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import ir.amirroid.mafiauto.ui_models.settings.Language
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenLibraries: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val configuration by viewModel.settingsConfiguration.collectAsStateWithLifecycle()

    CollapsingTopAppBarScaffold(
        title = { MText(stringResource(Resources.strings.settings)) },
        navigationIcon = { BackButton(onBack) },
    ) { paddingValues ->
        SettingsConfiguration(
            configuration = configuration,
            onSelectLanguage = { viewModel.updateConfigurations(configuration.copy(language = it)) },
            onSelectTheme = { viewModel.updateConfigurations(configuration.copy(theme = it)) },
            onOpenLibraries = onOpenLibraries,
            contentPadding = paddingValues + defaultContentPadding()
        )
    }
}


@Composable
fun SettingsConfiguration(
    configuration: SettingsUiModel,
    onSelectLanguage: (Language) -> Unit,
    onSelectTheme: (AppThemeUiModel) -> Unit,
    onOpenLibraries: () -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    val uriHandler = LocalUriHandler.current
    LazyColumn(contentPadding = contentPadding, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item("language") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MText(stringResource(Resources.strings.language))
                MSegmentedButton(
                    items = Language.entries.sortedBy { it == configuration.language },
                    onClick = onSelectLanguage,
                    selectedItem = configuration.language,
                    modifier = Modifier.fillMaxWidth()
                ) { language ->
                    MText(
                        stringResource(language.displayName),
                    )
                }
            }
        }
        item("theme") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MText(stringResource(Resources.strings.theme))
                MSegmentedButton(
                    items = AppThemeUiModel.entries,
                    onClick = onSelectTheme,
                    selectedItem = configuration.theme,
                    modifier = Modifier.fillMaxWidth()
                ) { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            Modifier.border(
                                1.dp,
                                AppTheme.colorScheme.background,
                                shape = CircleShape
                            ).clip(CircleShape).size(16.dp).background(theme.scheme.primary)
                        )
                        MText(stringResource(theme.displayName))
                    }
                }
            }
        }
        item("code") {
            MListItem(
                text = {
                    MText(stringResource(Resources.strings.viewSource))
                },
                onClick = {
                    uriHandler.openUri("https://github.com/amirroid/mafiauto")
                }
            )
        }
        item("libs") {
            MListItem(
                text = {
                    MText(stringResource(Resources.strings.openSourceLibraries))
                },
                onClick = onOpenLibraries
            )
        }
        item("version") {
            MText(
                text = stringResource(Resources.strings.version, AppInfo.version),
                style = AppTheme.typography.caption,
                modifier = Modifier.alpha(.7f)
            )
        }
    }
}