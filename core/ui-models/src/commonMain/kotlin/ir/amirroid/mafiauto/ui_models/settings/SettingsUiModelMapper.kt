package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.settings.Language
import ir.amirroid.mafiauto.domain.model.settings.Settings
import ir.amirroid.mafiauto.domain.model.settings.Theme

fun SettingsUiModel.toDomain() = Settings(
    language = Language.valueOf(language.name),
    theme = Theme.valueOf(theme.name),
    iconColor = iconColor.colorName,
    fontScale = fontScale
)