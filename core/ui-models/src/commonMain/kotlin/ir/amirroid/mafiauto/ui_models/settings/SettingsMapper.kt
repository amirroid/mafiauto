package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.Settings

fun Settings.toUiModel() = SettingsUiModel(
    language = Language.valueOf(language.name),
    languageCode = language.languageCode
)