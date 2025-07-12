package ir.amirroid.mafiauto.data.mappers.settings

import ir.amirroid.mafiauot.preferences.models.Language
import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauot.preferences.models.Theme
import ir.amirroid.mafiauto.domain.model.settings.Settings

fun Settings.toData(): SettingsConfig {
    return SettingsConfig(
        language = Language.valueOf(language.name),
        theme = Theme.valueOf(theme.name)
    )
}