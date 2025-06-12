package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor
import ir.amirroid.mafiauto.design_system.locales.LocalTextStyle
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShowStatusDialog(
    players: List<PlayerWithRoleUiModel>,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val deadAlignmentsWithCounts = remember(players) {
        players
            .groupBy { it.role.alignment }
            .mapValues { (_, group) -> group.count { it.player.let { player -> !player.isAlive || player.isKick } } }
            .entries
            .sortedByDescending { it.value }
    }

    MDialog(
        isVisible = visible,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MText(stringResource(Resources.strings.statusChecks), style = AppTheme.typography.h2)
            Spacer(Modifier)
            deadAlignmentsWithCounts.forEach { (alignment, deadCount) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MText(stringResource(alignment), modifier = Modifier.weight(1f))
                    MText(
                        buildAnnotatedString {
                            append(deadCount.toString())
                            pushStyle(LocalTextStyle.current.toSpanStyle())
                            append(" ")
                            append(stringResource(Resources.strings.kills))
                            pop()
                        },
                        style = AppTheme.typography.h3,
                        color = if (deadCount > 0) AppTheme.colorScheme.primary else LocalContentColor.current
                    )
                }
            }
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