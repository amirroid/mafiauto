package ir.amirroid.mafiauto.intro.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import compose.icons.evaicons.outline.ArrowIosForward
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.endPadding
import ir.amirroid.mafiauto.common.compose.modifiers.verticalPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.intro.viewmodel.LobbyViewModel
import ir.amirroid.mafiauto.resources.Resources
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun LobbyScreen(onBack: () -> Unit, viewModel: LobbyViewModel = koinViewModel()) {
    val roles = viewModel.roles
    val selectedRoles by viewModel.selectedRoles.collectAsStateWithLifecycle()
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        CollapsingTopAppBarScaffold(
            title = {
                MText(stringResource(Resources.strings.selectRoles))
            },
            navigationIcon = {
                MIconButton(onClick = onBack) {
                    MIcon(
                        imageVector = EvaIcons.Outline.ArrowIosBack,
                        contentDescription = null
                    )
                }
            },
            hazeState = hazeState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = defaultContentPadding() + paddingValues + PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(roles, key = { it.key }) { descriptor ->
                    val selected by remember {
                        derivedStateOf {
                            selectedRoles.contains(descriptor)
                        }
                    }
                    MToggleListItem(
                        text = {
                            MText(stringResource(descriptor.name))
                        },
                        selected = selected,
                        onClick = {
                            viewModel.toggle(descriptor)
                        }
                    )
                }
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .hazeEffect(hazeState, hazeStyle)
                .endPadding()
                .verticalPadding()
                .navigationBarsPadding(),
            contentAlignment = Alignment.CenterEnd
        ) {
            MButton(
                onClick = {},
                modifier = Modifier
                    .height(48.dp)
            ) {
                MText(stringResource(Resources.strings.next))
                MIcon(
                    imageVector = EvaIcons.Outline.ArrowIosForward,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(20.dp)
                )
            }
        }
    }
}