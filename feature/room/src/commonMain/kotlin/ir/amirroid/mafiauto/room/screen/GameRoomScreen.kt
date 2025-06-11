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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Plus
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MIconButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.room.dialogs.ShowPlayerRoleDialog
import ir.amirroid.mafiauto.room.viewmodel.GameRoomViewModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun GameRoomScreen(
    onBack: () -> Unit,
    viewModel: GameRoomViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val players = state.players
    val pickedPlayerToShowRole = state.pickedPlayerToShowRole
    val statusChecksCount = state.statusChecksCount

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
            statusChecksCount = statusChecksCount,
            onCheckStatus = viewModel::addStatusCheck,
            modifier = Modifier
                .fillMaxWidth()
                .hazeEffect(hazeState, hazeStyle)
                .allPadding()
                .imePadding()
                .navigationBarsPadding()
        )
    }
    pickedPlayerToShowRole?.let {
        ShowPlayerRoleDialog(it, onDismissRequest = viewModel::clearPickedPlayer)
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
            MListItem(
                text = {
                    MText(item.player.name)
                },
                onClick = { onPickPlayer.invoke(item) },
                modifier = Modifier.height(48.dp),
                shape = AppTheme.shapes.medium
            )
        }
    }
}

@Composable
fun GameRoomAppBar() {
    MText(stringResource(Resources.strings.gameRoom))
}


@Composable
private fun BottomBar(
    statusChecksCount: Int,
    onCheckStatus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        StatusChecksSection(
            count = statusChecksCount,
            onCheck = onCheckStatus,
        )
    }
}


@Composable
fun StatusChecksSection(
    count: Int,
    onCheck: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        MText("${stringResource(Resources.strings.statusChecks)}:", modifier = Modifier.weight(1f))
        MText(count.toString(), style = AppTheme.typography.h2)
        MIconButton(onClick = onCheck) {
            MIcon(EvaIcons.Outline.Plus, contentDescription = null)
        }
    }
}