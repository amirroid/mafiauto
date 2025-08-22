package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.settings.Settings
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import ir.amirroid.mafiauto.ui_models.icon.AppIconUiModel

fun Settings.toUiModel() = SettingsUiModel(
    language = Language.valueOf(language.name),
    languageCode = language.languageCode,
    theme = AppThemeUiModel.valueOf(theme.name),
    iconColor = AppIconUiModel.entries.first { it.colorName == iconColor },
    fontScale = fontScale
)