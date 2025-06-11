package ir.amirroid.mafiauto.room.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
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
    val players = viewModel.players
    val pickedPlayerToShowRole = viewModel.pickedPlayerToShowRole
    val hazeState = rememberHazeState()
    val hazeStyle: HazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)

    SmallTopAppBarScaffold(
        title = { GameRoomAppBar() },
        navigationIcon = { BackButton(onBack = onBack) },
        hazeState = hazeState,
        hazeStyle = hazeStyle
    ) { paddingValues ->
        Column {
            RoomPlayersList(
                players = players,
                contentPadding = defaultContentPadding() + paddingValues,
                modifier = Modifier.weight(1f),
                onPickPlayer = { viewModel.pickedPlayerToShowRole = it }
            )
        }
    }
    pickedPlayerToShowRole?.let {
        ShowPlayerRoleDialog(it, onDismissRequest = { viewModel.pickedPlayerToShowRole = null })
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