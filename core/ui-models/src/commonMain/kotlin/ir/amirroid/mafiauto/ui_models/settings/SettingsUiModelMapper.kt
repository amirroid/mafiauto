package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.Language
import ir.amirroid.mafiauto.domain.model.Settings
import ir.amirroid.mafiauto.domain.model.Theme

fun SettingsUiModel.toDomain() = Settings(
    language = Language.valueOf(language.name),
    theme = Theme.valueOf(theme.name)
)