package ir.amirroid.mafiauto.settings.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.common.app.response.onError
import ir.amirroid.mafiauto.common.app.response.onSuccess
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.loading.MLoading
import ir.amirroid.mafiauto.design_system.components.segmented_button.MSegmentedButton
import ir.amirroid.mafiauto.design_system.components.snakebar.LocalSnakeBarControllerState
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.settings.dialog.NewUpdateBottomSheet
import ir.amirroid.mafiauto.settings.viewmodel.SettingsViewModel
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.core.ColorScheme
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import ir.amirroid.mafiauto.theme.utils.CutRoundedCornerShape
import ir.amirroid.mafiauto.ui_models.icon.AppIconUiModel
import ir.amirroid.mafiauto.ui_models.settings.Language
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenLibraries: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val configuration by viewModel.settingsConfiguration.collectAsStateWithLifecycle()
    val updateInfoResponse by viewModel.updateInfo.collectAsStateWithLifecycle()
    val snakeBarController = LocalSnakeBarControllerState.current
    LaunchedEffect(updateInfoResponse) {
        updateInfoResponse.onError {
            snakeBarController.show(it.message)
            viewModel.clearUpdateInfoResponse()
        }
    }

    CollapsingTopAppBarScaffold(
        title = { MText(stringResource(Resources.strings.settings)) },
        navigationIcon = { BackButton(onBack) },
    ) { paddingValues ->
        SettingsConfiguration(
            configuration = configuration,
            onSelectLanguage = { viewModel.updateConfigurations(configuration.copy(language = it)) },
            onSelectTheme = { viewModel.updateConfigurations(configuration.copy(theme = it)) },
            onOpenLibraries = onOpenLibraries,
            onCheckUpdate = viewModel::fetchUpdateInfo,
            onChangeIconColor = viewModel::updateIconColor,
            contentPadding = paddingValues + defaultContentPadding(),
            fetchingUpdateInfo = updateInfoResponse is Response.Loading
        )
    }

    updateInfoResponse.onSuccess {
        NewUpdateBottomSheet(
            updateInfo = it,
            onDismissRequest = viewModel::clearUpdateInfoResponse
        )
    }
}


@Composable
fun SettingsConfiguration(
    configuration: SettingsUiModel,
    onSelectLanguage: (Language) -> Unit,
    onSelectTheme: (AppThemeUiModel) -> Unit,
    onChangeIconColor: (AppIconUiModel) -> Unit,
    onCheckUpdate: () -> Unit,
    onOpenLibraries: () -> Unit,
    fetchingUpdateInfo: Boolean,
    contentPadding: PaddingValues = PaddingValues()
) {
    val uriHandler = LocalUriHandler.current
    LazyColumn(contentPadding = contentPadding, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item("language") {
            Column {
                MText(stringResource(Resources.strings.language))
                Spacer(Modifier.height(8.dp))
                LanguageSelector(
                    selectedLanguage = configuration.language,
                    onLanguageSelected = onSelectLanguage
                )
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
                        ThemeSchemePreview(theme.scheme)
                        MText(stringResource(theme.displayName))
                    }
                }
            }
        }
        item("icon") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                MText(stringResource(Resources.strings.iconColor))
                MSegmentedButton(
                    items = AppIconUiModel.entries,
                    onClick = onChangeIconColor,
                    selectedItem = configuration.iconColor,
                    modifier = Modifier.fillMaxWidth()
                ) { iconInfo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(iconInfo.icon),
                            contentDescription = null,
                            modifier = Modifier.clip(AppTheme.shapes.small).size(20.dp)
                        )
                        MText(stringResource(iconInfo.displayName))
                    }
                }
            }
        }
        item("updater") {
            MListItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        MText(stringResource(Resources.strings.checkForNewUpdate))
                        AnimatedVisibility(fetchingUpdateInfo) {
                            MLoading(size = 24.dp)
                        }
                    }
                },
                onClick = onCheckUpdate,
                enabled = !fetchingUpdateInfo
            )
        }
        item("code") {
            MListItem(
                text = {
                    MText(stringResource(Resources.strings.viewSource))
                },
                onClick = {
                    uriHandler.openUri(AppInfo.githubLink)
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

@Composable
private fun LanguageSelector(
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit
) {
    val chunkedLanguages = remember {
        Language.entries.chunked(2).map { it.toImmutableList() }.toImmutableList()
    }

    val shapeTemplate = AppTheme.shapes.large
    val smallCorner = CornerSize(4.dp)

    chunkedLanguages.forEachIndexed { index, languages ->
        val shape = when (index) {
            0 -> shapeTemplate.copy(
                bottomStart = smallCorner,
                bottomEnd = smallCorner
            )

            chunkedLanguages.lastIndex -> shapeTemplate.copy(
                topStart = smallCorner,
                topEnd = smallCorner
            )

            else -> CutRoundedCornerShape(
                topStart = smallCorner,
                topEnd = smallCorner,
                bottomStart = smallCorner,
                bottomEnd = smallCorner
            )
        }

        MSegmentedButton(
            items = languages,
            selectedItem = selectedLanguage,
            onClick = onLanguageSelected,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            shape = shape
        ) { language ->
            MText(text = stringResource(language.displayName))
        }
    }
}


@Composable
fun ThemeSchemePreview(scheme: ColorScheme, strokeWidth: Dp = 1.dp) {
    Canvas(Modifier.size(16.dp)) {
        val strokeWidthPx = strokeWidth.toPx()
        drawCircle(scheme.primary)
        drawCircle(
            color = scheme.onPrimary,
            style = Stroke(strokeWidthPx)
        )
        val circlePath = Path().apply {
            addArc(
                oval = Rect(
                    Offset(strokeWidthPx.times(2), strokeWidthPx.times(2)),
                    Size(size.width - strokeWidthPx.times(4), size.height - strokeWidthPx.times(4))
                ),
                -90f,
                190f
            )
        }
        drawPath(circlePath, scheme.onPrimary)
    }
}