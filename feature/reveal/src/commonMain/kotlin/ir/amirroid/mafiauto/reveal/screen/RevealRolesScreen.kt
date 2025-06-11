package ir.amirroid.mafiauto.reveal.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import compose.icons.evaicons.outline.ArrowIosForward
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.common.compose.utils.PlatformCapabilities
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.appbar.rememberCollapsingAppBarState
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MOutlinedButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.reveal.viewmodel.RevealRolesViewModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private val itemHeight = 200.dp

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun RevealRolesScreen(
    onBack: () -> Unit,
    onStartGame: () -> Unit,
    viewModel: RevealRolesViewModel = koinViewModel()
) {
    val playerWithRoles = viewModel.playerWithRoles
    val currentPlayerIndex = viewModel.currentPlayerIndex
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val collapsingAppBarState = rememberCollapsingAppBarState()

    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val statusBar = WindowInsets.statusBars
    val bottomPadding = run {
        with(density) {
            windowInfo.containerSize.height.toDp()
                .minus(itemHeight)
                .minus(statusBar.getTop(density).toDp())
                .minus(collapsingAppBarState.minHeightPx.toDp())
                .minus(12.dp)
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        CollapsingTopAppBarScaffold(
            title = { RevealTopBar() },
            navigationIcon = { BackButton(onBack) },
            hazeState = hazeState,
            state = collapsingAppBarState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            PlayersList(
                playersWithRoles = playerWithRoles,
                currentPlayerIndex = currentPlayerIndex,
                contentPadding = paddingValues + PaddingValues(bottom = bottomPadding) + defaultContentPadding()
            )
        }

        BottomBar(
            onNext = {
                viewModel.currentPlayerIndex =
                    currentPlayerIndex.plus(1).coerceAtMost(playerWithRoles.size.minus(1))
            },
            onPreviews = {
                viewModel.currentPlayerIndex = currentPlayerIndex.minus(1).coerceAtLeast(0)
            },
            onStart = onStartGame,
            currentPlayer = currentPlayerIndex + 1,
            isLastItem = currentPlayerIndex == playerWithRoles.size.minus(1),
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
fun PlayersList(
    playersWithRoles: List<PlayerWithRoleUiModel>,
    currentPlayerIndex: Int,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val state = rememberPagerState { playersWithRoles.size }

    LaunchedEffect(currentPlayerIndex) {
        state.animateScrollToPage(currentPlayerIndex)
    }

    VerticalPager(
        state = state,
        modifier = modifier,
        contentPadding = contentPadding,
        userScrollEnabled = false,
        key = { playersWithRoles[it].player.id },
        pageSize = PageSize.Fixed(itemHeight),
        pageSpacing = 12.dp
    ) { index ->
        val item = playersWithRoles[index]
        val isFocused = index == currentPlayerIndex
        PlayerWithRoleItem(item, isFocused)
    }
}

@Composable
fun PlayerWithRoleItem(item: PlayerWithRoleUiModel, isFocused: Boolean) {
    val contentColor by animateColorAsState(
        if (isFocused) AppTheme.colorScheme.onPrimary else AppTheme.colorScheme.onDisabled
    )
    val containerColor by animateColorAsState(
        if (isFocused) AppTheme.colorScheme.primary else AppTheme.colorScheme.disabled
    )
    val lockPercent by animateFloatAsState(
        if (isFocused) 0f else 1f
    )

    MSurface(
        contentColor = contentColor,
        color = containerColor,
        shape = AppTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            MText(text = item.player.name, style = AppTheme.typography.h3)
            Column(
                modifier = if (PlatformCapabilities.blurSupported) Modifier.blur(
                    8.dp * lockPercent,
                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                ) else Modifier.alpha(1f - lockPercent),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MText(
                    text = buildString {
                        append(stringResource(item.role.name))
                        append(" - ")
                        append(stringResource(item.role.alignment))
                    }
                )
                MText(text = stringResource(item.role.explanation))
            }
        }
    }
}


@Composable
private fun RevealTopBar() {
    MText(
        stringResource(Resources.strings.revealRoles)
    )
}

@Composable
private fun BottomBar(
    isLastItem: Boolean,
    currentPlayer: Int,
    onPreviews: () -> Unit,
    onNext: () -> Unit,
    onStart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MText(
            text = "${stringResource(Resources.strings.player)} $currentPlayer",
            style = AppTheme.typography.h2,
            modifier = Modifier.weight(1f)
        )
        AnimatedVisibility(
            visible = currentPlayer != 1,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            MOutlinedButton(
                onClick = onPreviews,
                modifier = Modifier.height(48.dp),
            ) {
                MIcon(
                    imageVector = EvaIcons.Outline.ArrowIosBack,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp).size(20.dp)
                )
            }
        }
        MButton(
            onClick = {
                if (isLastItem) onStart.invoke() else onNext.invoke()
            },
            modifier = Modifier.height(48.dp),
        ) {
            AnimatedContent(isLastItem) {
                if (it) {
                    MText(stringResource(Resources.strings.start))
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