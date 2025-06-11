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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosForward
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.assign_roles.viewmodel.AssignRolesViewModel
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.appbar.rememberCollapsingAppBarState
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun AssignRolesScreen(
    onBack: () -> Unit,
    onPickRoles: () -> Unit,
    viewModel: AssignRolesViewModel = koinViewModel()
) {
    val selectedRoles by viewModel.selectedRoles.collectAsStateWithLifecycle(emptyList())
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val collapsingAppBarState = rememberCollapsingAppBarState()

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
                contentPadding = paddingValues + PaddingValues(bottom = 80.dp) + defaultContentPadding()
            )
        }

        BottomBar(
            onNextClick = {
                viewModel.selectRoles()
                onPickRoles.invoke()
            },
            enabledNextPage = selectedRoles.size == viewModel.selectedPlayersCount,
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
    roles: List<RoleUiModel>,
    selectedRoles: List<RoleUiModel>,
    onToggle: (RoleUiModel) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(roles, key = { it.key }) { role ->
            val selected = remember(selectedRoles) { selectedRoles.contains(role) }
            MToggleListItem(
                text = { MText(stringResource(role.name)) },
                selected = selected,
                onClick = { onToggle.invoke(role) }
            )
        }
    }
}

@Composable
private fun BottomBar(
    onNextClick: () -> Unit,
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
            enabled = enabledNextPage
        ) {
            MText(stringResource(Resources.strings.next))
            MIcon(
                imageVector = EvaIcons.Outline.ArrowIosForward,
                contentDescription = null,
                modifier = Modifier.padding(start = 4.dp).size(20.dp)
            )
        }
    }
}