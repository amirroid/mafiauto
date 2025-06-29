package ir.amirroid.mafiauto.data.mappers.settings

import ir.amirroid.mafiauot.preferences.models.SettingsConfig
import ir.amirroid.mafiauto.domain.model.Language
import ir.amirroid.mafiauto.domain.model.Settings

fun SettingsConfig.toDomain() = Settings(
    language = Language.valueOf(language.name)
)