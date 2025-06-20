package ir.amirroid.mafiauto.groups.screen

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Plus
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.locales.LocalSharedTransitionScope
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MFloatingActionButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.groups.dialogs.AddNewGroupDialog
import ir.amirroid.mafiauto.groups.viewmodel.GroupsViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.group.GroupWithPlayersUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GroupsScreen(
    onSelectGroup: (groupId: Long) -> Unit,
    onBack: () -> Unit,
    animatedContentScope: AnimatedContentScope,
    viewModel: GroupsViewModel = koinViewModel()
) {
    val groups by viewModel.groups.collectAsStateWithLifecycle()

    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)

    Box(contentAlignment = Alignment.BottomEnd) {
        CollapsingTopAppBarScaffold(
            title = { MText(stringResource(Resources.strings.groups)) },
            navigationIcon = { BackButton(onBack) },
            hazeState = hazeState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            GroupsList(
                groupsWithPlayers = groups,
                onSelect = { onSelectGroup.invoke(it.groupId) },
                modifier = Modifier.fillMaxSize(),
                animatedContentScope,
                contentPadding = paddingValues + PaddingValues(bottom = 80.dp) + defaultContentPadding() + WindowInsets.navigationBars.asPaddingValues()
            )
        }
        MFloatingActionButton(
            onClick = viewModel::showAddNewGroup,
            modifier = Modifier.navigationBarsPadding().allPadding()
        ) {
            MIcon(
                imageVector = EvaIcons.Outline.Plus,
                contentDescription = null
            )
        }
    }
    if (viewModel.visibleAddNewGroup) {
        AddNewGroupDialog(
            onAdd = viewModel::addGroup,
            onDismissRequest = viewModel::hideAddNewGroup
        )
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GroupsList(
    groupsWithPlayers: List<GroupWithPlayersUiModel>,
    onSelect: (GroupWithPlayersUiModel) -> Unit,
    modifier: Modifier = Modifier,
    animatedContentScope: AnimatedContentScope,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp,
    ) {
        items(groupsWithPlayers, key = { it.groupId }) { item ->
            MSurface(
                onClick = { onSelect.invoke(item) },
                color = Color.Transparent,
                border = BorderStroke(1.dp, AppTheme.colorScheme.primary),
                shape = AppTheme.shapes.large,
                modifier = Modifier.animateItem().then(
                    with(sharedTransitionScope) {
                        Modifier.sharedBounds(
                            rememberSharedContentState(item.groupId),
                            animatedContentScope,
                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
                        )
                    }
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(12.dp).fillMaxWidth()
                ) {
                    MText(item.groupName, style = AppTheme.typography.h3)
                    MText(
                        remember(item) { item.players.joinToString("\n") { it.name } }
                    )
                }
            }
        }
    }
}