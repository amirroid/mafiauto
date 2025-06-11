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
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.room.viewmodel.GameRoomViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameRoomScreen(
    onBack: () -> Unit,
    viewModel: GameRoomViewModel = koinViewModel()
) {
    val players = viewModel.players
    SmallTopAppBarScaffold(
        title = { GameRoomAppBar() },
        navigationIcon = { BackButton(onBack = onBack) }
    ) { paddingValues ->
        Column {
            RoomPlayersList(
                players = players,
                contentPadding = defaultContentPadding() + paddingValues,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun RoomPlayersList(
    players: List<PlayerWithRole>,
    modifier: Modifier = Modifier,
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
                onClick = {},
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