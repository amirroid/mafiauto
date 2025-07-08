package ir.amirroid.mafiauto.data.mappers.settings

import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauto.domain.model.Language
import ir.amirroid.mafiauto.domain.model.Settings
import ir.amirroid.mafiauto.domain.model.Theme

fun SettingsConfig.toDomain() = Settings(
    language = Language.valueOf(language.name),
    theme = Theme.valueOf(theme.name)
)