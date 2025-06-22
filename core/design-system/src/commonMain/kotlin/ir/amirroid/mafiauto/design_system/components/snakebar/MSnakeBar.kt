package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MSnakeBar(
    text: String,
    type: SnackBaType,
    hazeState: HazeState,
    shape: Shape = AppTheme.shapes.large,
    strokeWidth: Dp = 1.dp
) {
    val primaryColor = AppTheme.colorScheme.primary
    Box(
        Modifier.fillMaxWidth()
            .statusBarsPadding()
            .padding(16.dp)
            .clip(shape)
            .border(strokeWidth, primaryColor, shape = shape)
            .hazeEffect(hazeState, HazeMaterials.thin(AppTheme.colorScheme.surface))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .statusBarsPadding()
    ) {
        Column {
            MText(
                stringResource(type.displayName),
                style = AppTheme.typography.caption,
                modifier = Modifier.alpha(.7f)
            )
            MText(text, style = AppTheme.typography.h4)
        }
    }
}