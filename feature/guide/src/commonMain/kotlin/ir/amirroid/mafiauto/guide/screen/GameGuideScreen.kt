package ir.amirroid.mafiauto.guide.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.surface.MSurface
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.guide.components.ExpandToggleIcon
import ir.amirroid.mafiauto.guide.viewmodel.GameGuideViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameGuideScreen(
    onBack: () -> Unit,
    viewModel: GameGuideViewModel = koinViewModel()
) {
    val roles = viewModel.roles

    CollapsingTopAppBarScaffold(
        title = { MText(stringResource(Resources.strings.gameGuide)) },
        navigationIcon = { BackButton(onBack = onBack) },
    ) { paddingValues ->
        RolesGuidList(
            roles = roles,
            contentPadding = paddingValues + defaultContentPadding()
        )
    }
}

@Composable
fun RolesGuidList(
    roles: ImmutableList<RoleUiModel>,
    contentPadding: PaddingValues = PaddingValues()
) {
    val sortedList = remember(roles) { roles.sortedBy { it.executionOrder } }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(sortedList, key = { _, role -> role.key }) { index, role ->
            RoleItem(role = role, index = index)
        }
    }
}


@Composable
fun RoleItem(role: RoleUiModel, index: Int) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val contentColor by animateColorAsState(
        if (isExpanded) AppTheme.colorScheme.onPrimary else AppTheme.colorScheme.onSurface
    )
    val containerColor by animateColorAsState(
        if (isExpanded) AppTheme.colorScheme.primary else AppTheme.colorScheme.surface
    )
    val percent by animateFloatAsState(
        if (isExpanded) 1f else 0f
    )
    MSurface(
        contentColor = contentColor,
        color = containerColor,
        shape = AppTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MText(
                    text = "${index + 1}. ${stringResource(role.name)}",
                    style = AppTheme.typography.h3,
                    modifier = Modifier.weight(1f)
                )
                MIconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(20.dp)
                ) {
                    ExpandToggleIcon(expanded = isExpanded)
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MText(
                    text = buildString {
                        append(stringResource(role.formattedAlignment))
                        if (role.maxAbilityUses != Int.MAX_VALUE) {
                            append(" - ${role.maxAbilityUses}X")
                        }
                    },
                    style = AppTheme.typography.caption
                )
                MText(
                    text = stringResource(role.explanation),
                    style = lerp(AppTheme.typography.caption, AppTheme.typography.h3, percent)
                )
            }
        }
    }
}