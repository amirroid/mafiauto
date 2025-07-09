package ir.amirroid.mafiauto.theme.locales

import androidx.compose.runtime.staticCompositionLocalOf
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel

val LocalAppTheme =
    staticCompositionLocalOf<AppThemeUiModel> { error("AppThemeUiModel not provided") }