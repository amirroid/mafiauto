package ir.amirroid.mafiauto.common.compose.utils

import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

actual object LocaleUtils {
    actual fun getDefaultLanguage(): String {
        val preferredLanguages = NSLocale.preferredLanguages
        val primaryLanguage = preferredLanguages.firstOrNull() as? String
        return primaryLanguage?.substringBefore("-") ?: "en"
    }

    actual fun setDefaultLanguage(language: String) {
        NSUserDefaults.standardUserDefaults.setObject(
            arrayListOf(language), "AppleLanguages"
        )
    }
}