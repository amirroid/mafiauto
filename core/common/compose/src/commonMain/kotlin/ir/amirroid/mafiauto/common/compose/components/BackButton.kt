package ir.amirroid.mafiauto.common.compose.components

import androidx.compose.runtime.Composable
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import ir.amirroid.mafiauto.common.compose.utils.autoMirrorIosBackwardIcon
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon

@Composable
fun BackButton(onBack: () -> Unit) {
    MIconButton(onClick = onBack) {
        MIcon(
            imageVector = autoMirrorIosBackwardIcon(),
            contentDescription = null
        )
    }
}