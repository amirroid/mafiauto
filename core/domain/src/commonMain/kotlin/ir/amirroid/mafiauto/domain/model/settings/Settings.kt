package ir.amirroid.mafiauto.domain.model.settings

import ir.amirroid.mafiauto.common.compose.utils.LocaleUtils


enum class Language(
    val languageCode: String
) {
    ENGLISH("en"), PERSIAN("fa"), FRENCH("fr"), ARABIC("ar")
}

enum class Theme {
    RED, GREEN, BLUE
}

data class Settings(
    val language: Language,
    val theme: Theme,
    val iconColor: String,
    val fontScale: Float?
) {
    companion object {
        val defaultSettings by lazy {
            val currentLanguage = LocaleUtils.getDefaultLanguage()
            Settings(
                language = Language.entries.find { it.languageCode == currentLanguage }
                    ?: Language.ENGLISH,
                theme = Theme.RED,
                iconColor = "red",
                fontScale = null
            )
        }
    }
}