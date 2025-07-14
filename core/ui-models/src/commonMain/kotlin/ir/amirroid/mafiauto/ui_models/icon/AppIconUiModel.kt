package ir.amirroid.mafiauto.ui_models.icon

import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class AppIconUiModel(
    val displayName: StringResource,
    val colorName: String,
    val icon: DrawableResource
) {
    RED(Resources.strings.red, "red", Resources.drawable.logoRed),
    GREEN(Resources.strings.green, "green", Resources.drawable.logoGreen),
    BLUE(Resources.strings.blue, "blue", Resources.drawable.logoBlue)
}