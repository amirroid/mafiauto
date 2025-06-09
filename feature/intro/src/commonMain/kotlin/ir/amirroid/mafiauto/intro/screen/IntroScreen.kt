package ir.amirroid.mafiauto.intro.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.common.compose.components.ScreenContent
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IntroScreen(
    onStartGame: () -> Unit
) {
    val colorScheme = AppTheme.colorScheme
    val blackPartFraction = .7f

    ScreenContent {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(Resources.drawable.background),
                    contentScale = ContentScale.Crop
                )
                .drawWithCache {
                    onDrawWithContent {
                        val brush = Brush.verticalGradient(
                            0f to Color.Transparent,
                            blackPartFraction to colorScheme.background
                        )
                        drawRect(brush)
                        drawContent()
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f - blackPartFraction)
                    .padding(32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.Start
            ) {
                MText(
                    text = stringResource(Resources.strings.introTitle),
                    style = AppTheme.typography.h1,
                    color = colorScheme.onBackground
                )

                MText(
                    text = stringResource(Resources.strings.introDescription),
                    style = AppTheme.typography.body,
                    color = colorScheme.onBackground.copy(alpha = 0.9f)
                )

                MButton(
                    onClick = onStartGame,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    MText(
                        text = stringResource(Resources.strings.startGame),
                        style = AppTheme.typography.button
                    )
                }
            }
        }
    }
}