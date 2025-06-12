package ir.amirroid.mafiauto.design_system.components.snakebar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalHapticFeedback
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
    text: StringResource,
    hazeState: HazeState
) {
    val primaryColor = AppTheme.colorScheme.primary
    Box(
        Modifier.fillMaxWidth()
            .hazeEffect(hazeState, HazeMaterials.thin(AppTheme.colorScheme.surface))
            .drawWithContent {
                drawContent()
                drawLine(
                    primaryColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .statusBarsPadding()
    ) {
        MText(stringResource(text), style = AppTheme.typography.h4)
    }
}