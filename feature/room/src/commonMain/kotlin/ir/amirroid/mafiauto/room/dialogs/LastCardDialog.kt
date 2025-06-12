package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.common.compose.base.snapshotStateListSaver
import ir.amirroid.mafiauto.common.compose.components.AnimatedList
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.last_card.LastCardUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun LastCardDialog(
    cards: List<LastCardUiModel>,
    players: List<PlayerWithRoleUiModel>,
    targetPlayer: PlayerWithRoleUiModel,
    onApply: (LastCardUiModel, List<PlayerWithRoleUiModel>) -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    var selectedCardIndex by remember { mutableStateOf<Int?>(null) }

    var pickingPlayers by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedPlayers = rememberSaveable(saver = snapshotStateListSaver()) {
        mutableStateListOf<PlayerWithRoleUiModel>()
    }

    LaunchedEffect(selectedCardIndex) {
        if (selectedCardIndex == null) return@LaunchedEffect
        delay(4000)
        pickingPlayers = true
    }

    BaseBlurredPopup(
        isVisible = visible,
        onDismissRequest = {
            onApply.invoke(
                cards[selectedCardIndex!!],
                selectedPlayers
            )
        },
        dismissOnBackPress = false
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(0.83f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(pickingPlayers) { inPick ->
                if (inPick) {
                    MText(
                        stringResource(Resources.strings.selectTargetPlayers),
                        style = AppTheme.typography.h2
                    )
                } else {
                    MText(
                        buildAnnotatedString {
                            append(stringResource(Resources.strings.lastCard))
                            append(" - ")
                            withStyle(SpanStyle(color = AppTheme.colorScheme.primary)) {
                                append(targetPlayer.player.name)
                            }
                        },
                        style = AppTheme.typography.h2
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            if (pickingPlayers && selectedCardIndex != null) {
                PlayersList(
                    players = players,
                    selectedPlayers = selectedPlayers,
                    requiredTargetCount = cards[selectedCardIndex!!].targetCount
                )
                Spacer(Modifier.height(16.dp))
                MButton(
                    onClick = { visible = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MText(stringResource(Resources.strings.ok))
                }
            } else {
                LastCardList(
                    cards = cards,
                    selectedCardIndex = selectedCardIndex,
                    onCardSelected = { selectedCardIndex = it }
                )
            }
        }
    }
}

@Composable
private fun LastCardList(
    cards: List<LastCardUiModel>,
    selectedCardIndex: Int?,
    onCardSelected: (Int) -> Unit
) {
    AnimatedList(
        items = cards,
        itemKey = { _, it -> it.key },
        justItemVisible = selectedCardIndex
    ) { index, card ->
        val isSelected = selectedCardIndex == index
        MListItem(
            text = {
                AnimatedContent(isSelected) { selected ->
                    if (selected) {
                        MText(stringResource(card.name))
                    } else {
                        MText("${stringResource(Resources.strings.card)} ${index + 1}")
                    }
                }
            },
            onClick = { onCardSelected.invoke(index) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            colors = if (isSelected)
                ListItemDefaults.filledColors
            else
                ListItemDefaults.defaultColors
        )
    }
}

@Composable
private fun PlayersList(
    players: List<PlayerWithRoleUiModel>,
    selectedPlayers: SnapshotStateList<PlayerWithRoleUiModel>,
    requiredTargetCount: Int,
) {
    AnimatedList(
        items = players,
        itemKey = { _, it -> it.player.id },
        modifier = Modifier.heightIn(max = 350.dp)
    ) { _, player ->
        val selected by remember {
            derivedStateOf { selectedPlayers.contains(player) }
        }
        MToggleListItem(
            text = {
                MText(player.player.name)
            },
            selected = selected,
            onClick = {
                if (selectedPlayers.contains(player)) {
                    selectedPlayers.remove(player)
                } else {
                    if (selectedPlayers.size < requiredTargetCount)
                        selectedPlayers.add(player)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            enabled = selected || selectedPlayers.size < requiredTargetCount
        )
    }
}