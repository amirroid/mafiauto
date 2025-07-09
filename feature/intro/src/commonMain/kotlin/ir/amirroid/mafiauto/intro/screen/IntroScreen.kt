package ir.amirroid.mafiauto.intro.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Settings
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeInputScale
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.ScreenContent
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalAppTheme
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

const val blackPartFraction = .7f

@OptIn(ExperimentalHazeApi::class)
@Composable
fun IntroScreen(
    onStartGame: () -> Unit,
    onSettingsClick: () -> Unit
) {
    ScreenContent {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Background()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f - blackPartFraction)
                    .padding(32.dp),
                horizontalAlignment = Alignment.Start
            ) {
                MText(
                    text = stringResource(Resources.strings.introTitle),
                    color = AppTheme.colorScheme.onBackground,
                    style = AppTheme.typography.h1
                )

                MText(
                    text = stringResource(Resources.strings.introDescription),
                    style = AppTheme.typography.body,
                    color = AppTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp)
                )

                MButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(48.dp),
                    onClick = onStartGame
                ) {
                    MText(
                        text = stringResource(Resources.strings.startNewGame),
                        style = AppTheme.typography.button
                    )
                }
            }
            MIconButton(
                onClick = onSettingsClick,
                modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().allPadding()
            ) {
                MIcon(
                    imageVector = EvaIcons.Outline.Settings,
                    contentDescription = null
                )
            }
        }
    }
}

private fun getBackgroundImageForTheme(theme: AppThemeUiModel?) = when (theme) {
    AppThemeUiModel.BLUE -> Resources.drawable.backgroundBlue
    AppThemeUiModel.GREEN -> Resources.drawable.backgroundGreen
    else -> Resources.drawable.backgroundRed
}

@OptIn(ExperimentalHazeApi::class)
@Composable
private fun Background(
    modifier: Modifier = Modifier
) {
    val colorScheme = AppTheme.colorScheme
    val theme = LocalAppTheme.current
    val backgroundImage = remember(theme) { getBackgroundImageForTheme(theme) }
    val hazeState = rememberHazeState()

    Image(
        painter = painterResource(backgroundImage),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .hazeSource(hazeState),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .hazeEffect(hazeState) {
                mask = Brush.verticalGradient(
                    0.6f to Color.Transparent,
                    blackPartFraction to Color.Black
                )
                inputScale = HazeInputScale.Auto
                blurEnabled = true
                tints = listOf(
                    HazeTint(colorScheme.background.copy(alpha = 0.3f))
                )
            }
    )
}