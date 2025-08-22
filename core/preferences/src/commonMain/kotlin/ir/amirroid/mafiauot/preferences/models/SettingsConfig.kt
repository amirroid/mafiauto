package ir.amirroid.mafiauot.preferences.models

import kotlinx.serialization.Serializable

enum class Language {
    ENGLISH, PERSIAN, FRENCH, ARABIC
}

enum class Theme {
    RED, GREEN, BLUE
}

@Serializable
data class SettingsConfig(
    val language: Language,
    val theme: Theme,
    val iconColor: String,
    val fontScale: Float? = null
)