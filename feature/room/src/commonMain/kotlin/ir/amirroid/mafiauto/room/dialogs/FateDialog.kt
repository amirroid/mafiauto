package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

@Composable
fun FateDialog(
    targetPlayer: PlayerWithRoleUiModel,
    sameVotesDefenders: List<PlayerWithRoleUiModel>,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }
    val shuffledDefenders = remember {
        mutableStateListOf(*sameVotesDefenders.toTypedArray())
    }

    LaunchedEffect(Unit) {
        repeat(4) {
            val previous = shuffledDefenders.toList()
            var newList: List<PlayerWithRoleUiModel>
            do {
                newList = previous.shuffled()
            } while (newList == previous && shuffledDefenders.size > 1)

            shuffledDefenders.clear()
            shuffledDefenders.addAll(newList)

            delay(1250)
        }
        shuffledDefenders.removeAll { it.player.id != targetPlayer.player.id }
    }

    BaseBlurredPopup(isVisible = visible, onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.fillMaxWidth(.82f).widthIn(max = 400.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MText(stringResource(Resources.strings.deathLottery), style = AppTheme.typography.h2)
            Spacer(Modifier.height(24.dp))
            LazyColumn(
                modifier = Modifier.heightIn(max = 400.dp).animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(shuffledDefenders, key = { it.player.id }) { item ->
                    val colors =
                        if (shuffledDefenders.size == 1) ListItemDefaults.filledColors else ListItemDefaults.defaultColors
                    MListItem(
                        text = { MText(item.player.name) },
                        modifier = Modifier.fillMaxWidth().animateItem(),
                        onClick = {},
                        colors = colors
                    )
                }
            }
            MButton(
                onClick = { visible = false },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                enabled = shuffledDefenders.size == 1
            ) {
                MText(stringResource(Resources.strings.ok))
            }
        }
    }
}