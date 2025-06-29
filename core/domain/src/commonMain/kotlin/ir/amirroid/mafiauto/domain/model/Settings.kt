package ir.amirroid.mafiauto.domain.model

import ir.amirroid.mafiauto.common.compose.utils.LocaleUtils


enum class Language(
    val languageCode: String
) {
    ENGLISH("en"), PERSIAN("fa")
}

data class Settings(
    val language: Language
) {
    companion object {
        val defaultSettings by lazy {
            val currentLanguage = LocaleUtils.getDefaultLanguage()
            Settings(
                language = Language.entries.find { it.languageCode == currentLanguage }
                    ?: Language.ENGLISH
            )
        }
    }
}