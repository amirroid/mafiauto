package ir.amirroid.mafiauto.ui_models.settings

import ir.amirroid.mafiauto.domain.model.Language
import ir.amirroid.mafiauto.domain.model.Settings

fun SettingsUiModel.toDomain() = Settings(
    language = Language.valueOf(language.name)
)