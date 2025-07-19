package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.confettikit.compose.ConfettiKit
import io.github.vinceglb.confettikit.core.Angle
import io.github.vinceglb.confettikit.core.Party
import io.github.vinceglb.confettikit.core.Position
import io.github.vinceglb.confettikit.core.Spread
import io.github.vinceglb.confettikit.core.emitter.Emitter
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MOutlinedButton
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.domain.model.role.Alignment
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.getRelatedStringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds

@Composable
fun WinnerDialog(alignment: Alignment, onShowSummary: () -> Unit, onDismissRequest: () -> Unit) {
    var visible by remember { mutableStateOf(true) }
    var shouldShowSummary by remember { mutableStateOf(true) }

    BaseBlurredPopup(isVisible = visible, onDismissRequest = {
        if (shouldShowSummary) onShowSummary() else onDismissRequest()
    }) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            ConfettiKit(
                parties = listOf(
                    Party(
                        speed = 0f,
                        maxSpeed = 15f,
                        damping = 0.9f,
                        angle = Angle.BOTTOM,
                        spread = Spread.ROUND,
                        timeToLive = 4000,
                        emitter = Emitter(duration = 3.5.seconds).perSecond(100),
                        position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
                    )
                ),
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxWidth(.82f).widthIn(max = 400.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                MText(
                    stringResource(Resources.strings.endResult),
                    style = AppTheme.typography.caption
                )
                MText(
                    text = stringResource(
                        Resources.strings.gameWinnerAnnouncement,
                        stringResource(alignment.getRelatedStringResource())
                    ),
                    style = AppTheme.typography.h1
                )
                MOutlinedButton(
                    onClick = {
                        visible = false
                        shouldShowSummary = true
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                ) {
                    MText(stringResource(Resources.strings.viewGameSummary))
                }
                MButton(
                    onClick = {
                        visible = false
                        shouldShowSummary = false
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                ) {
                    MText(stringResource(Resources.strings.playAgain))
                }
            }
        }
    }
}