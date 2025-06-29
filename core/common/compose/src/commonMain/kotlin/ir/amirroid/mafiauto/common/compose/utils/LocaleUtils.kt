package ir.amirroid.mafiauto.common.compose.utils

expect object LocaleUtils {
    fun getDefaultLanguage(): String
    fun setDefaultLanguage(language: String)
}