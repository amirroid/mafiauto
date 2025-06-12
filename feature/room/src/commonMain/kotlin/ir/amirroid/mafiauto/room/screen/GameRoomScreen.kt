package ir.amirroid.mafiauto.room.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Fill
import compose.icons.evaicons.Outline
import compose.icons.evaicons.fill.ArrowIosForward
import compose.icons.evaicons.outline.Minus
import compose.icons.evaicons.outline.Plus
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.thenIf
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.room.dialogs.ShowPlayerRoleDialog
import ir.amirroid.mafiauto.room.dialogs.ShowStatusDialog
import ir.amirroid.mafiauto.room.dialogs.VotingDialog
import ir.amirroid.mafiauto.room.viewmodel.GameRoomViewModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GameRoomScreen(
    onBack: () -> Unit, viewModel: GameRoomViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val players = state.players
    val pickedPlayerToShowRole = state.pickedPlayerToShowRole
    val statusChecksCount = state.statusChecksCount
    val showStatus = state.showStatus
    val currentPhase = state.currentPhase
    val canCheckStatus = remember(players) { players.count { it.player.isInGame.not() } > 0 }

    val hazeState = rememberHazeState()
    val hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SmallTopAppBarScaffold(
            title = { GameRoomAppBar() },
            navigationIcon = { BackButton(onBack = onBack) },
            hazeState = hazeState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            Column {
                RoomPlayersList(
                    players = players,
                    contentPadding = defaultContentPadding() + paddingValues + PaddingValues(bottom = 300.dp),
                    modifier = Modifier.weight(1f),
                    onPickPlayer = viewModel::pickPlayerToShowRole
                )
            }
        }
        BottomBar(
            currentPhase = currentPhase,
            statusChecksCount = statusChecksCount,
            canCheckStatus = canCheckStatus,
            onCheckStatus = viewModel::increaseStatusCheck,
            onDecreaseCheckStatus = viewModel::decreaseStatusCheck,
            onNextPhase = viewModel::nextPhase,
            modifier = Modifier.fillMaxWidth().hazeEffect(hazeState, hazeStyle).allPadding()
                .imePadding().navigationBarsPadding()
        )
    }
    pickedPlayerToShowRole?.let {
        ShowPlayerRoleDialog(
            it,
            onKick = { viewModel.kick(it.player.id) },
            onUnKick = { viewModel.unKick(it.player.id) },
            onDismissRequest = viewModel::clearPickedPlayer
        )
    }
    if (showStatus) {
        ShowStatusDialog(players = players, onDismissRequest = viewModel::hideShowStatus)
    }
    VotingPhaseDialog(
        currentPhase = currentPhase,
        players = players,
        onStartDefending = viewModel::startDefending,
        onNextPhase = viewModel::nextPhase
    )
}

@Composable
fun VotingPhaseDialog(
    currentPhase: GamePhaseUiModel,
    players: List<PlayerWithRoleUiModel>,
    onStartDefending: (Map<PlayerWithRoleUiModel, Int>) -> Boolean,
    onNextPhase: () -> Unit
) {
    if (currentPhase is GamePhaseUiModel.Voting || currentPhase is GamePhaseUiModel.Defending) {
        val isVotingPhase = currentPhase is GamePhaseUiModel.Voting
        val votePlayers = when (currentPhase) {
            is GamePhaseUiModel.Defending -> currentPhase.defenders
            else -> players
        }
        val totalVotes = remember(players) {
            players.filter { it.player.isInGame }.size - 1
        }
        VotingDialog(
            players = votePlayers,
            totalVotes = totalVotes,
            onSelectedPlayers = { votes ->
                if (isVotingPhase) {
                    onStartDefending(votes)
                } else {
                    onNextPhase()
                    true
                }
            },
            onDismissRequest = onNextPhase
        )
    }
}

@Composable
fun RoomPlayersList(
    players: List<PlayerWithRoleUiModel>,
    modifier: Modifier = Modifier,
    onPickPlayer: (PlayerWithRoleUiModel) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(players, key = { it.player.id }) { item ->
            PlayerItem(
                playerWithRole = item, onClick = { onPickPlayer.invoke(item) })
        }
    }
}

@Composable
fun PlayerItem(playerWithRole: PlayerWithRoleUiModel, onClick: () -> Unit) {
    val colors = when {
        playerWithRole.player.isAlive -> ListItemDefaults.defaultColors
        else -> ListItemDefaults.filledColors
    }
    val primaryColor = AppTheme.colorScheme.primary
    MListItem(
        text = {
            MText(
                playerWithRole.player.name,
                modifier = Modifier.thenIf(playerWithRole.player.isKick) {
                    drawWithContent {
                        drawContent()
                        drawLine(
                            color = primaryColor,
                            start = center.copy(x = 0f),
                            end = center.copy(x = size.width),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }.fillMaxWidth()
            )
        },
        onClick = onClick,
        modifier = Modifier.height(48.dp),
        shape = AppTheme.shapes.medium,
        colors = colors
    )
}

@Composable
fun GameRoomAppBar() {
    MText(stringResource(Resources.strings.gameRoom))
}


@Composable
private fun BottomBar(
    statusChecksCount: Int,
    currentPhase: GamePhaseUiModel,
    canCheckStatus: Boolean,
    onCheckStatus: () -> Unit,
    onNextPhase: () -> Unit,
    onDecreaseCheckStatus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatusChecksSection(
            count = statusChecksCount,
            canCheckStatus = canCheckStatus,
            onCheck = onCheckStatus,
            onDecrease = onDecreaseCheckStatus
        )
        CurrentPhaseSection(currentPhase = currentPhase, onNextPhase = onNextPhase)
    }
}


@Composable
fun StatusChecksSection(
    count: Int,
    canCheckStatus: Boolean,
    onDecrease: () -> Unit,
    onCheck: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MText("${stringResource(Resources.strings.statusChecks)}:", modifier = Modifier.weight(1f))
        MIconButton(onClick = onDecrease) {
            MIcon(EvaIcons.Outline.Minus, contentDescription = null)
        }
        MText(count.toString(), style = AppTheme.typography.h2)
        MIconButton(onClick = onCheck, enabled = canCheckStatus) {
            MIcon(EvaIcons.Outline.Plus, contentDescription = null)
        }
    }
}


@Composable
fun CurrentPhaseSection(
    currentPhase: GamePhaseUiModel, onNextPhase: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MText(
            stringResource(currentPhase.displayName),
            style = AppTheme.typography.h3,
            modifier = Modifier.weight(1f)
        )
        MButton(
            onClick = onNextPhase,
            modifier = Modifier.height(48.dp),
            enabled = currentPhase == GamePhaseUiModel.Day
        ) {
            MText(stringResource(Resources.strings.voting))
            MIcon(
                imageVector = EvaIcons.Fill.ArrowIosForward,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp).size(20.dp)
            )
        }
    }
}