package ir.amirroid.mafiauot.preferences.models

import kotlinx.serialization.Serializable

enum class Language {
    ENGLISH, PERSIAN
}

@Serializable
data class SettingsConfig(
    val language: Language
)