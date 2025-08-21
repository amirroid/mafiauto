package ir.amirroid.mafiauto.assign_roles.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.assign_roles.dialogs.RoleExplanationDialog
import ir.amirroid.mafiauto.assign_roles.viewmodel.AssignRolesViewModel
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.onSecondaryClick
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.common.compose.utils.autoMirrorIosForwardIcon
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.appbar.rememberCollapsingAppBarState
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.snakebar.LocalSnakeBarControllerState
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.theme.locales.LocalContentColor
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun AssignRolesScreen(
    onBack: () -> Unit,
    onPickRoles: () -> Unit,
    viewModel: AssignRolesViewModel = koinViewModel()
) {
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val collapsingAppBarState = rememberCollapsingAppBarState()

    val selectedRoles by viewModel.selectedRoles.collectAsStateWithLifecycle(emptyImmutableList())
    val previewRole = viewModel.selectedRoleToPreviewExplanation
    val enabledNextPage = remember(selectedRoles) { viewModel.checkConditions() }

    val snakeBarController = LocalSnakeBarControllerState.current

    Box(contentAlignment = Alignment.BottomCenter) {
        CollapsingTopAppBarScaffold(
            title = { AssignRolesTopBar(selectedCount = selectedRoles.size) },
            navigationIcon = { BackButton(onBack) },
            hazeState = hazeState,
            state = collapsingAppBarState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            RolesList(
                roles = viewModel.roles,
                selectedRoles = selectedRoles,
                onToggle = viewModel::toggleRole,
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues + PaddingValues(bottom = 80.dp) + defaultContentPadding(),
                onShowPreview = viewModel::showPreview
            )
        }

        BottomBar(
            onNextClick = {
                viewModel.selectRoles()
                onPickRoles.invoke()
            },
            onWarning = { snakeBarController.show(Resources.strings.assignRolesHint) },
            enabledNextPage = enabledNextPage,
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(hazeState, hazeStyle) {
                    blurEnabled = true
                }
                .allPadding()
                .imePadding()
                .navigationBarsPadding()
        )
    }

    previewRole?.let {
        RoleExplanationDialog(it, onDismissRequest = viewModel::dismissPreview)
    }
}

@Composable
private fun AssignRolesTopBar(selectedCount: Int) {
    MText(
        buildAnnotatedString {
            append(stringResource(Resources.strings.selectRoles))
            withStyle(SpanStyle(color = LocalContentColor.current.copy(.7f), fontSize = 16.sp)) {
                append("  - $selectedCount ")
                append(stringResource(Resources.strings.selected))
            }
        }
    )
}

@Composable
private fun RolesList(
    roles: ImmutableList<RoleUiModel>,
    selectedRoles: ImmutableList<RoleUiModel>,
    onToggle: (RoleUiModel) -> Unit,
    onShowPreview: (RoleUiModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val groupedRoles = remember(roles) {
        roles.groupBy { it.formattedAlignment }
    }
    val shape = AppTheme.shapes.large
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        groupedRoles.forEach { (alignment, roles) ->
            item("alignment$alignment", contentType = "alignment", span = { GridItemSpan(2) }) {
                MText(
                    text = stringResource(alignment),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            itemsIndexed(
                roles,
                key = { _, item -> "role${item.key}" },
                contentType = { _, _ -> "role" }) { index, role ->
                val selected = remember(selectedRoles) { selectedRoles.contains(role) }
                val itemShape = when {
                    index == roles.size - 1 && roles.size % 2 != 0 -> shape
                    index % 2 == 0 -> shape.copy(
                        bottomEnd = CornerSize(2.dp),
                        topEnd = CornerSize(2.dp)
                    )

                    else -> shape.copy(
                        bottomStart = CornerSize(2.dp),
                        topStart = CornerSize(2.dp)
                    )
                }
                MToggleListItem(
                    text = { MText(stringResource(role.name)) },
                    selected = selected,
                    onClick = { onToggle.invoke(role) },
                    shape = itemShape,
                    onLongClick = { onShowPreview.invoke(role) },
                    modifier = Modifier.onSecondaryClick { onShowPreview.invoke(role) }
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    onNextClick: () -> Unit,
    onWarning: () -> Unit,
    modifier: Modifier = Modifier,
    enabledNextPage: Boolean
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        MButton(
            onClick = onNextClick,
            modifier = Modifier.height(48.dp),
            enabled = enabledNextPage,
            onClickWhenDisabled = onWarning
        ) {
            MText(stringResource(Resources.strings.next))
            MIcon(
                imageVector = autoMirrorIosForwardIcon(),
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp).size(20.dp)
            )
        }
    }
}