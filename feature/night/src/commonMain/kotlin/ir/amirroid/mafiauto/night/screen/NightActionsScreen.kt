package ir.amirroid.mafiauto.night.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import compose.icons.evaicons.outline.ArrowIosDownward
import compose.icons.evaicons.outline.ArrowIosForward
import compose.icons.evaicons.outline.Lock
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.PlatformBackHandler
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.horizontalPadding
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.button.MOutlinedButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.night.viewmodel.NightActionsViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.night_target_otpions.NightTargetOptionsUiModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun NightActionsScreen(
    onBack: () -> Unit,
    viewModel: NightActionsViewModel = koinViewModel()
) {
    val hazeState = rememberHazeState()
    val hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val currentPhase by viewModel.currentPhase.collectAsStateWithLifecycle()
    if (currentPhase !is GamePhaseUiModel.Night) return
    val nightPhase = currentPhase as GamePhaseUiModel.Night
    val options = nightPhase.options
    val selectedPlayers by viewModel.selectedPlayers.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState { nightPhase.options.size }

    PlatformBackHandler {}

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SmallTopAppBarScaffold(
            title = { NightActionsAppBar() },
            hazeState = hazeState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize().padding(top = 12.dp),
                userScrollEnabled = false
            ) { index ->
                val playerOptions = options[index]
                SelectOptionPlayersList(
                    playerOptions = playerOptions,
                    selectedPlayers = selectedPlayers,
                    onSelect = { target -> viewModel.togglePlayer(playerOptions.player, target) },
                    contentPadding = paddingValues
                )
            }
        }
        val nextEnabled = selectedPlayers[options[pagerState.currentPage].player] != null ||
                options[pagerState.currentPage].player.let {
                    it.player.canUseAbilityToNight.not() || it.role.isOptionalAbility
                }
        BottomBar(
            enabledNext = nextEnabled,
            enabledPreviews = pagerState.currentPage > 0,
            onNext = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
            onPreviews = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
            isLastItem = pagerState.currentPage == options.size - 1,
            onComplete = {
                viewModel.applyActions()
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
    playerOptions: NightTargetOptionsUiModel,
    selectedPlayers: Map<PlayerWithRoleUiModel, PlayerWithRoleUiModel>,
    onSelect: (PlayerWithRoleUiModel) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    val currentPlayerRole = playerOptions.player
    val selectedPlayer = selectedPlayers[currentPlayerRole]
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .horizontalPadding()
            .padding(contentPadding)
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MText(
                buildString {
                    append(currentPlayerRole.player.name)
                    append(" - ")
                    append(stringResource(currentPlayerRole.role.name))
                    if (currentPlayerRole.role.isOptionalAbility) {
                        append(" - ${stringResource(Resources.strings.optional)}")
                    }
                    if (currentPlayerRole.player.hasLimitToUseAbilities) {
                        append(" - ${currentPlayerRole.player.remainingAbilityUses}X")
                    }
                }
            )
            if (!currentPlayerRole.player.canUseAbility) {
                MIcon(
                    EvaIcons.Outline.Lock,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            MIconButton(onClick = { isExpanded = !isExpanded }) {
                val rotation by animateFloatAsState(if (isExpanded) 180f else 0f)
                MIcon(
                    EvaIcons.Outline.ArrowIosDownward,
                    contentDescription = null,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotation)
                )
            }
        }

        AnimatedVisibility(visible = isExpanded) {
            MText(
                stringResource(currentPlayerRole.role.explanation),
                style = AppTheme.typography.caption
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 16.dp)
        ) {
            items(
                items = playerOptions.availableTargets,
                key = { it.player.id }
            ) { targetPlayer ->
                MToggleListItem(
                    selected = selectedPlayer == targetPlayer,
                    onClick = { onSelect(targetPlayer) },
                    text = { MText(targetPlayer.player.name) },
                    enabled = currentPlayerRole.player.canUseAbilityToNight
                )
            }
        }
    }
}

@Composable
fun NightActionsAppBar() {
    MText(stringResource(Resources.strings.nightActions))
}


@Composable
fun BottomBar(
    enabledNext: Boolean,
    enabledPreviews: Boolean,
    isLastItem: Boolean,
    onComplete: () -> Unit,
    onNext: () -> Unit,
    onPreviews: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        MOutlinedButton(
            onClick = onPreviews,
            modifier = Modifier.height(48.dp),
            enabled = enabledPreviews
        ) {
            MIcon(
                imageVector = EvaIcons.Outline.ArrowIosBack,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            MText(
                stringResource(Resources.strings.back),
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        MButton(
            onClick = if (isLastItem) onComplete else onNext,
            modifier = Modifier.height(48.dp),
            enabled = enabledNext
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