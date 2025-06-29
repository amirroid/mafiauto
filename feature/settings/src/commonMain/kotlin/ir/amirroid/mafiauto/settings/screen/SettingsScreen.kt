package ir.amirroid.mafiauto.settings.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.segmented_button.MSegmentedButton
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.settings.viewmodel.SettingsViewModel
import ir.amirroid.mafiauto.ui_models.settings.Language
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
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
            contentPadding = paddingValues + defaultContentPadding()
        )
    }
}


@Composable
fun SettingsConfiguration(
    configuration: SettingsUiModel,
    onSelectLanguage: (Language) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(contentPadding = contentPadding) {
        item("language") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MText(stringResource(Resources.strings.language))
                MSegmentedButton(
                    items = Language.entries,
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
    }
}