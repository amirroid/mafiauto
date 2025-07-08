package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Minus
import compose.icons.evaicons.outline.Plus
import ir.amirroid.mafiauto.common.compose.base.snapshotStateMapSaver
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.dialog.MDialog
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource

@Composable
fun VotingDialog(
    players: ImmutableList<PlayerWithRoleUiModel>,
    allPlayers: ImmutableList<PlayerWithRoleUiModel>,
    totalVotes: Int,
    onSelectedPlayers: (Map<PlayerWithRoleUiModel, Int>) -> Boolean,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val votes = rememberSaveable(
        saver = snapshotStateMapSaver(),
    ) { mutableStateMapOf<Long, Int>() }
    val inGamePlayers = remember(players) { players.filter { it.player.isInGame } }

    LaunchedEffect(players) { votes.clear() }

    MDialog(
        isVisible = visible,
        onDismissRequest = onDismissRequest,
        dismissOnBackPress = false
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MText(
                text = stringResource(Resources.strings.voting),
                style = AppTheme.typography.h2
            )

            LazyColumn(
                modifier = Modifier.height(350.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(inGamePlayers, key = { it.player.id }) { playerRole ->
                    val playerId = playerRole.player.id
                    val voteCount = votes[playerId] ?: 0

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.animateItem()
                    ) {
                        MText(
                            text = playerRole.player.name,
                            modifier = Modifier.weight(1f)
                        )
                        MIconButton(
                            onClick = {
                                votes[playerId] = voteCount.minus(1).coerceAtLeast(0)
                            },
                            enabled = voteCount > 0
                        ) {
                            MIcon(
                                imageVector = EvaIcons.Outline.Minus,
                                contentDescription = null
                            )
                        }
                        MText(
                            text = voteCount.toString(),
                            style = AppTheme.typography.h3
                        )
                        MIconButton(
                            onClick = {
                                votes[playerId] =
                                    voteCount.plus(1)
                                        .coerceAtMost(totalVotes)
                            },
                            enabled = voteCount < totalVotes
                        ) {
                            MIcon(
                                imageVector = EvaIcons.Outline.Plus,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            MButton(
                onClick = {
                    val votesCount = allPlayers
                        .filter { it.player.isInGame }
                        .associateWith { votes[it.player.id] ?: 0 }
                    visible = !onSelectedPlayers.invoke(votesCount)
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                MText(text = stringResource(Resources.strings.ok))
            }
        }
    }
}