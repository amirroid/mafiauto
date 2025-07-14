package ir.amirroid.mafiauto.ui_models.settings

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.LayoutDirection
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import org.jetbrains.compose.resources.StringResource

enum class Language(
    val displayName: StringResource,
    val layoutDirection: LayoutDirection = LayoutDirection.Ltr,
) {
    ENGLISH(Resources.strings.english),
    FRENCH(Resources.strings.french),
    PERSIAN(Resources.strings.persian, LayoutDirection.Rtl),
    ARABIC(Resources.strings.arabic, LayoutDirection.Rtl)
}

@Immutable
data class SettingsUiModel(
    val language: Language,
    val languageCode: String,
    val theme: AppThemeUiModel,
    val iconColor: String
)