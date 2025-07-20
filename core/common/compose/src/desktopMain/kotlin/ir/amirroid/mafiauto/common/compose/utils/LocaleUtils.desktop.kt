package ir.amirroid.mafiauto.common.compose.utils

import java.util.Locale

actual object LocaleUtils {
    actual fun getDefaultLanguage(): String {
        return Locale.getDefault().language.substringBefore("-")
    }

    actual fun setDefaultLanguage(language: String) {
        val newLocale = Locale.of(language)
        Locale.setDefault(newLocale)
    }
}