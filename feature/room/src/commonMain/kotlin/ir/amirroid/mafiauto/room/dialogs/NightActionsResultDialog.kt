package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.result.NightActionsResultUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun NightActionsResultDialog(
    result: NightActionsResultUiModel,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    MDialog(isVisible = visible, onDismissRequest = onDismissRequest) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            MText(stringResource(Resources.strings.nightActionsResult), style = AppTheme.typography.h2)
            Spacer(Modifier)
            Item(
                key = stringResource(Resources.strings.deathCount),
                value = result.deathCount.toString()
            )
            Item(
                key = stringResource(Resources.strings.revivedCount),
                value = result.revivedCount.toString()
            )
            Spacer(Modifier)
            MButton(
                onClick = { visible = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                MText(stringResource(Resources.strings.ok))
            }
        }
    }
}

@Composable
private fun Item(key: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        MText(key, modifier = Modifier.weight(1f))
        MText(
            value,
            style = AppTheme.typography.h3,
        )
    }
}