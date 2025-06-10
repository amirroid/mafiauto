package ir.amirroid.mafiauto.intro.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosForward
import compose.icons.evaicons.outline.Plus
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.CollapsingTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.appbar.rememberCollapsingAppBarState
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.field.MTextField
import ir.amirroid.mafiauto.design_system.components.field.TextFieldsDefaults
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.design_system.locales.LocalContentColor
import ir.amirroid.mafiauto.domain.model.Player
import ir.amirroid.mafiauto.intro.viewmodel.LobbyViewModel
import ir.amirroid.mafiauto.resources.Resources
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun LobbyScreen(
    onBack: () -> Unit,
    onPickPlayers: () -> Unit,
    viewModel: LobbyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hazeState = rememberHazeState()
    val hazeStyle = HazeMaterials.thin(AppTheme.colorScheme.surface)
    val focusManager = LocalFocusManager.current
    val collapsingAppBarState = rememberCollapsingAppBarState()
    val scope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.BottomCenter) {
        CollapsingTopAppBarScaffold(
            title = { LobbyTopBar(selectedCount = state.selectedPlayers.size) },
            navigationIcon = { BackButton(onBack) },
            hazeState = hazeState,
            state = collapsingAppBarState,
            hazeStyle = hazeStyle
        ) { paddingValues ->
            PlayerList(
                players = state.players,
                selectedPlayers = state.selectedPlayers,
                onToggle = viewModel::togglePlayerSelection,
                onDelete = {
                    viewModel.deletePlayer(it)
                    scope.launch { collapsingAppBarState.animateTo(collapsingAppBarState.maxHeightPx) }
                },
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues + PaddingValues(bottom = 80.dp) + defaultContentPadding()
            )
        }

        AddPlayerBar(
            newPlayerName = state.newPlayerName,
            onNewPlayerNameChange = viewModel::updateNewPlayerName,
            onAddClick = {
                focusManager.clearFocus()
                viewModel.addNewPlayer()
            },
            onNextClick = {
                viewModel.selectPlayers()
                onPickPlayers.invoke()
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
private fun LobbyTopBar(selectedCount: Int) {
    MText(
        buildAnnotatedString {
            append(stringResource(Resources.strings.selectPlayers))
            withStyle(SpanStyle(color = LocalContentColor.current.copy(.7f), fontSize = 16.sp)) {
                append("  - $selectedCount ")
                append(stringResource(Resources.strings.selected))
            }
        }
    )
}

@Composable
private fun PlayerList(
    players: List<Player>,
    selectedPlayers: List<Player>,
    onToggle: (Player) -> Unit,
    onDelete: (Player) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(players, key = { it.id }) { player ->
            val selected = remember(selectedPlayers) { selectedPlayers.contains(player) }
            PlayerItem(
                player = player,
                selected = selected,
                onClick = { onToggle(player) },
                onDelete = { onDelete(player) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun PlayerItem(
    player: Player,
    selected: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (SwipeToDismissBoxValue.Settled != it) {
                onDelete.invoke()
                true
            } else false
        },
        positionalThreshold = { fullWidth -> fullWidth * .4f }
    )
    SwipeToDismissBox(
        state, backgroundContent = {}, modifier = modifier
    ) {
        MToggleListItem(
            text = { MText(player.name) },
            selected = selected,
            onClick = { onClick.invoke() }
        )
    }
}

@Composable
private fun AddPlayerBar(
    newPlayerName: String,
    onNewPlayerNameChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MTextField(
            value = newPlayerName,
            onValueChange = onNewPlayerNameChange,
            modifier = Modifier.weight(1f),
            colors = TextFieldsDefaults.outlinedTextFieldColors,
            placeholder = {
                MText(stringResource(Resources.strings.addNewPlayerHint))
            },
            singleLine = true
        )
        MButton(
            onClick = {
                if (newPlayerName.isNotEmpty()) onAddClick.invoke() else onNextClick.invoke()
            },
            modifier = Modifier.height(48.dp)
        ) {
            AnimatedContent(newPlayerName.isEmpty()) { isEmpty ->
                if (isEmpty) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        MText(stringResource(Resources.strings.next))
                        MIcon(
                            imageVector = EvaIcons.Outline.ArrowIosForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        MText(stringResource(Resources.strings.add))
                        MIcon(
                            imageVector = EvaIcons.Outline.Plus,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}