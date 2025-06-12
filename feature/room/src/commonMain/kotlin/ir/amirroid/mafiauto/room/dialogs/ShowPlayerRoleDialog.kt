package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MOutlinedButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShowPlayerRoleDialog(
    playerWithRole: PlayerWithRoleUiModel,
    onKick: () -> Unit,
    onUnKick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    var visible by remember {
        mutableStateOf(true)
    }
    val kicked = playerWithRole.player.isKick
    MDialog(
        isVisible = visible,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            MText(text = playerWithRole.player.name, style = AppTheme.typography.h3)
            MText(
                text = buildString {
                    append(stringResource(playerWithRole.role.name))
                    append(" - ")
                    append(stringResource(playerWithRole.role.alignment))
                },
                modifier = Modifier.padding(top = 8.dp)
            )
            MText(
                text = stringResource(playerWithRole.role.explanation),
                modifier = Modifier.padding(top = 16.dp)
            )
            MOutlinedButton(onClick = {
                if (kicked) onUnKick.invoke() else onKick.invoke()
                visible = false
            }, modifier = Modifier.padding(top = 16.dp).fillMaxWidth()) {
                if (kicked) {
                    MText(stringResource(Resources.strings.unKick))
                } else {
                    MText(stringResource(Resources.strings.kick))
                }
            }
            MButton(
                onClick = {
                    visible = false
                }, modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
            ) {
                MText(stringResource(Resources.strings.ok))
            }
        }
    }
}