package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.common.compose.base.snapshotStateListSaver
import ir.amirroid.mafiauto.common.compose.components.AnimatedList
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.ui_models.effect.PlayerEffectUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun SelectPlayersForEffectDialog(
    players: List<PlayerWithRoleUiModel>,
    effect: PlayerEffectUiModel,
    onSelectPlayers: (List<PlayerWithRoleUiModel>) -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    val selectedPlayers = rememberSaveable(saver = snapshotStateListSaver()) {
        mutableStateListOf<PlayerWithRoleUiModel>()
    }
    val requiredTargetCount = when (effect) {
        is PlayerEffectUiModel.DayActionEffect -> effect.nightActionRequiredPicks
        else -> 1
    }
    val isInGamePlayers = remember(players) {
        players.filter { it.player.isInGame }
    }

    BaseBlurredPopup(
        isVisible = visible,
        onDismissRequest = {
            onSelectPlayers.invoke(selectedPlayers)
        },
        dismissOnBackPress = false
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(0.83f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MText(
                stringResource(Resources.strings.selectTargetPlayers),
                style = AppTheme.typography.h2
            )
            AnimatedList(
                items = isInGamePlayers,
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
            Spacer(Modifier.height(16.dp))
            MButton(
                onClick = { visible = false },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedPlayers.size == requiredTargetCount
            ) {
                MText(stringResource(Resources.strings.ok))
            }
        }
    }
}