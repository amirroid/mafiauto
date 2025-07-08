package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.Settings
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel

fun Settings.toUiModel() = SettingsUiModel(
    language = Language.valueOf(language.name),
    languageCode = language.languageCode,
    theme = AppThemeUiModel.valueOf(theme.name)
)