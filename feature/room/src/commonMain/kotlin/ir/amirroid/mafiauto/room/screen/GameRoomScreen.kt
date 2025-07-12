package ir.amirroid.mafiauto.room.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Minus
import compose.icons.evaicons.outline.Plus
import compose.icons.evaicons.outline.VolumeMute
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultVerticalContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.horizontalPadding
import ir.amirroid.mafiauto.common.compose.modifiers.thenIf
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.common.compose.utils.autoMirrorIosForwardIcon
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.snakebar.LocalSnakeBarControllerState
import ir.amirroid.mafiauto.design_system.components.snakebar.SnackBaType
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalContentColor
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.room.dialogs.FateDialog
import ir.amirroid.mafiauto.room.dialogs.LastCardDialog
import ir.amirroid.mafiauto.room.dialogs.NightActionsResultDialog
import ir.amirroid.mafiauto.room.dialogs.SelectPlayersForEffectDialog
import ir.amirroid.mafiauto.room.dialogs.ShowPlayerRoleDialog
import ir.amirroid.mafiauto.room.dialogs.ShowStatusDialog
import ir.amirroid.mafiauto.room.dialogs.VotingDialog
import ir.amirroid.mafiauto.room.dialogs.WinnerDialog
import ir.amirroid.mafiauto.room.viewmodel.GameRoomViewModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GameRoomScreen(
    onBack: () -> Unit,
    onNight: () -> Unit,
    onFinalDebate: () -> Unit,
    viewModel: GameRoomViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val players = state.players
    val pickedPlayerToApplyEffect = state.pickedPlayerToApplyEffect
    val pickedPlayerToShowRole = state.pickedPlayerToShowRole
    val statusChecksCount = state.statusChecksCount
    val showStatus = state.showStatus
    val currentPhase = state.currentPhase
    val currentDay = state.currentDay
    val currentTurn = state.currentTurn
    val canCheckStatus = remember(players) { players.count { it.player.isInGame.not() } > 0 }

    val hazeState = rememberHazeState()
    val hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val snakeBarController = LocalSnakeBarControllerState.current

    LaunchedEffect(currentPhase) {
        if (currentPhase is GamePhaseUiModel.Night) onNight.invoke()
        if (currentPhase is GamePhaseUiModel.FinalDebate) onFinalDebate.invoke()
    }

    LaunchedEffect(Unit) {
        viewModel.messages.collect {
            snakeBarController.show(it, type = SnackBaType.INFO)
        }
    }

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
                    contentPadding = defaultVerticalContentPadding() + paddingValues + PaddingValues(
                        bottom = 300.dp
                    ),
                    modifier = Modifier.weight(1f),
                    onPickPlayer = viewModel::pickPlayerToShowRole,
                    currentTurn = currentTurn
                )
            }
        }
        BottomBar(
            currentPhase = currentPhase,
            currentDay = currentDay,
            statusChecksCount = statusChecksCount,
            canCheckStatus = canCheckStatus,
            onCheckStatus = viewModel::increaseStatusCheck,
            onDecreaseCheckStatus = viewModel::decreaseStatusCheck,
            onNextPhase = viewModel::nextPhase,
            modifier = Modifier.fillMaxWidth().hazeEffect(hazeState, hazeStyle).allPadding()
                .imePadding().navigationBarsPadding()
        )
    }
    pickedPlayerToShowRole?.let { playerRole ->
        ShowPlayerRoleDialog(
            playerRole,
            onKick = { viewModel.kick(playerRole.player.id) },
            onUnKick = { viewModel.unKick(playerRole.player.id) },
            onSelectPlayersForEffect = { effect ->
                viewModel.showSelectionPlayersForEffect(
                    effect = effect,
                    player = playerRole
                )
            },
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
        onSubmitDefense = viewModel::submitDefense,
        onNextPhase = viewModel::nextPhase
    )
    if (currentPhase is GamePhaseUiModel.LastCard) {
        LastCardDialog(
            cards = state.lastCards,
            targetPlayerRole = currentPhase.player,
            onApply = viewModel::applyLastCard,
            players = players
        )
    }
    if (currentPhase is GamePhaseUiModel.Fate) {
        FateDialog(
            targetPlayer = currentPhase.targetPlayer,
            sameVotesDefenders = currentPhase.sameVotesDefenders,
            onDismissRequest = viewModel::handleFate
        )
    }
    if (currentPhase is GamePhaseUiModel.Result) {
        NightActionsResultDialog(
            result = currentPhase.result,
            onDismissRequest = viewModel::nextPhase
        )
    }
    if (currentPhase is GamePhaseUiModel.End) {
        WinnerDialog(
            alignment = currentPhase.winnerAlignment,
            onDismissRequest = onBack
        )
    }
    pickedPlayerToApplyEffect?.let {
        SelectPlayersForEffectDialog(
            players = players,
            effect = it.effect,
            onSelectPlayers = viewModel::applyPlayerEffect
        )
    }
}

@Composable
fun VotingPhaseDialog(
    currentPhase: GamePhaseUiModel,
    players: ImmutableList<PlayerWithRoleUiModel>,
    onStartDefending: (Map<PlayerWithRoleUiModel, Int>) -> Boolean,
    onSubmitDefense: (Map<PlayerWithRoleUiModel, Int>) -> Unit,
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
                    onSubmitDefense(votes)
                    true
                }
            },
            allPlayers = players,
            onDismissRequest = onNextPhase
        )
    }
}

@Composable
fun RoomPlayersList(
    players: ImmutableList<PlayerWithRoleUiModel>,
    modifier: Modifier = Modifier,
    onPickPlayer: (PlayerWithRoleUiModel) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    currentTurn: Int
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        itemsIndexed(players, key = { _, item -> item.player.id }) { index, item ->
            PlayerItem(
                playerWithRole = item,
                onClick = { onPickPlayer.invoke(item) },
                isCurrentTurn = currentTurn == index
            )
        }
    }
}

@Composable
fun PlayerItem(playerWithRole: PlayerWithRoleUiModel, isCurrentTurn: Boolean, onClick: () -> Unit) {
    val colors = when {
        playerWithRole.player.isAlive -> ListItemDefaults.defaultColors
        else -> ListItemDefaults.filledColors
    }
    val primaryColor = AppTheme.colorScheme.primary
    MListItem(
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MText(
                    playerWithRole.player.name,
                    modifier = Modifier
                        .thenIf(playerWithRole.player.isKick) {
                            drawWithContent {
                                drawContent()
                                drawLine(
                                    color = primaryColor,
                                    start = center.copy(x = 0f),
                                    end = center.copy(x = size.width),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                        .weight(1f)
                )
                playerWithRole.player.effects.forEach {
                    it.buttonInfo?.Image()
                }
                AnimatedVisibility(
                    playerWithRole.player.isSilenced,
                    enter = scaleIn(),
                    exit = scaleOut()
                ) {
                    MIcon(
                        imageVector = EvaIcons.Outline.VolumeMute,
                        contentDescription = null
                    )
                }
            }
        },
        onClick = onClick,
        modifier = Modifier.drawBehind {
            if (isCurrentTurn) {
                val path = Path().apply {
                    addRoundRect(
                        roundRect = RoundRect(
                            rect = Rect(
                                offset = Offset.Zero,
                                size = Size(4.dp.toPx(), size.height)
                            ),
                            bottomRight = CornerRadius(50f),
                            topRight = CornerRadius(50f),
                        )
                    )
                }
                drawPath(path, primaryColor)
            }
        }.height(48.dp).horizontalPadding(),
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
    currentDay: Int,
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
        CurrentPhaseSection(
            currentPhase = currentPhase,
            currentDay = currentDay,
            onNextPhase = onNextPhase
        )
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
        MText(
            "${stringResource(Resources.strings.statusChecks)}:",
            modifier = Modifier.weight(1f)
        )
        MIconButton(onClick = onDecrease, enabled = count > 0) {
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
    currentDay: Int,
    currentPhase: GamePhaseUiModel, onNextPhase: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MText(
            buildAnnotatedString {
                append(stringResource(currentPhase.displayName))
                withStyle(
                    AppTheme.typography.caption.toSpanStyle()
                        .copy(color = LocalContentColor.current.copy(.7f))
                ) {
                    append("  ")
                    if (currentDay == 0) {
                        append(stringResource(Resources.strings.introductionRound))
                    } else append("${stringResource(Resources.strings.round)} $currentDay")
                }
            },
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
                imageVector = autoMirrorIosForwardIcon(),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp).size(20.dp)
            )
        }
    }
}