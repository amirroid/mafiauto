package ir.amirroid.mafiauto.ui_models.effect

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource

sealed interface EffectButtonInfo {
    val text: StringResource

    @Composable
    fun Image()

    data class ResourceIcon(
        val icon: DrawableResource,
        override val text: StringResource,
    ) : EffectButtonInfo {
        @Composable
        override fun Image() {
            MIcon(painterResource(icon), contentDescription = null)
        }
    }

    data class ImageVectorIcon(
        val icon: ImageVector,
        override val text: StringResource,
    ) : EffectButtonInfo {
        @Composable
        override fun Image() {
            MIcon(icon, contentDescription = null)
        }
    }
}