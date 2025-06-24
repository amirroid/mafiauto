package ir.amirroid.mafiauto.final_debate.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosForward
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.PlatformBackHandler
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.horizontalPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.final_debate.viewmodel.FinalDebateViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun FinalDebateScreen(
    onBack: () -> Unit,
    viewModel: FinalDebateViewModel = koinViewModel()
) {
    val hazeState = rememberHazeState()
    val hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val players by viewModel.players.collectAsStateWithLifecycle()
    val inFinalDebatePlayerWithRoles = remember(players) {
        players.filter { it.player.isInGame }
    }
    val selectedPlayers by viewModel.selectedPlayers.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { inFinalDebatePlayerWithRoles.size }
    PlatformBackHandler {}

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SmallTopAppBarScaffold(
            title = { MText(stringResource(Resources.strings.finalDebate)) },
            hazeState = hazeState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize().padding(top = 12.dp),
                userScrollEnabled = false,
                key = { index -> inFinalDebatePlayerWithRoles[index].player.id }
            ) { index ->
                val playerWithRole = inFinalDebatePlayerWithRoles[index]
                SelectOptionPlayersList(
                    player = playerWithRole,
                    selectedPlayer = selectedPlayers[playerWithRole],
                    availableTargets = remember(inFinalDebatePlayerWithRoles) {
                        inFinalDebatePlayerWithRoles.filter { it.player.id != playerWithRole.player.id }
                    },
                    onSelect = { target -> viewModel.togglePlayer(playerWithRole, target) },
                    contentPadding = paddingValues,
                )
            }
        }
        BottomBar(
            onNext = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
            isLastItem = pagerState.currentPage == inFinalDebatePlayerWithRoles.size - 1,
            onComplete = {
                viewModel.completeDebate()
                onBack.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(hazeState, hazeStyle)
                .allPadding()
                .imePadding()
                .navigationBarsPadding()
        )
    }
}

@Composable
fun SelectOptionPlayersList(
    player: PlayerWithRoleUiModel,
    selectedPlayer: PlayerWithRoleUiModel?,
    availableTargets: List<PlayerWithRoleUiModel>,
    onSelect: (PlayerWithRoleUiModel) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .horizontalPadding()
            .padding(contentPadding)
            .statusBarsPadding()
    ) {
        MText(
            buildAnnotatedString {
                withStyle(AppTheme.typography.h4.toSpanStyle()) {
                    appendLine(player.player.name)
                }
                append(stringResource(Resources.strings.selectDesiredCivilian))
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 80.dp
            ) + WindowInsets.navigationBars.asPaddingValues()
        ) {
            items(
                items = availableTargets,
                key = { it.player.id }
            ) { targetPlayer ->
                val selected = selectedPlayer == targetPlayer
                MToggleListItem(
                    selected = selected,
                    onClick = { onSelect(targetPlayer) },
                    text = { MText(targetPlayer.player.name) },
                    enabled = selectedPlayer == null || selected
                )
            }
        }
    }
}


@Composable
fun BottomBar(
    isLastItem: Boolean,
    onComplete: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        MButton(
            onClick = if (isLastItem) onComplete else onNext,
            modifier = Modifier.height(48.dp),
        ) {
            AnimatedContent(isLastItem) {
                if (it) {
                    MText(stringResource(Resources.strings.complete))
                } else {
                    MText(stringResource(Resources.strings.next))
                }
            }
            MIcon(
                imageVector = EvaIcons.Outline.ArrowIosForward,
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp).size(20.dp)
            )
        }
    }
}