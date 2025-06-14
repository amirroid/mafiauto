package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowIosBack
import compose.icons.evaicons.outline.ArrowIosForward
import ir.amirroid.mafiauto.common.compose.base.snapshotStateMapSaver
import ir.amirroid.mafiauto.common.compose.modifiers.allPadding
import ir.amirroid.mafiauto.common.compose.modifiers.horizontalPadding
import ir.amirroid.mafiauto.common.compose.modifiers.topPadding
import ir.amirroid.mafiauto.design_system.components.button.MButton
import ir.amirroid.mafiauto.design_system.components.button.MOutlinedButton
import ir.amirroid.mafiauto.design_system.components.icon.MIcon
import ir.amirroid.mafiauto.design_system.components.list.selectable.MToggleListItem
import ir.amirroid.mafiauto.design_system.components.popup.BaseBlurredPopup
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.design_system.core.AppTheme
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.night_target_otpions.NightTargetOptionsUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun NightOptionsDialog(
    options: List<NightTargetOptionsUiModel>,
) {
    var visible by remember { mutableStateOf(true) }
    val pagerState = rememberPagerState { options.size }
    val selectedPlayers = rememberSaveable(saver = snapshotStateMapSaver()) {
        mutableStateMapOf<PlayerWithRoleUiModel, PlayerWithRoleUiModel>()
    }
    val scope = rememberCoroutineScope()

    BaseBlurredPopup(
        isVisible = visible,
        onDismissRequest = {},
        dismissOnBackPress = false
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            MText(
                stringResource(Resources.strings.nightActions),
                style = AppTheme.typography.caption,
                modifier = Modifier.topPadding().horizontalPadding()
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f).padding(top = 2.dp),
                userScrollEnabled = false
            ) { index ->
                val playerOptions = options[index]
                val selectedPlayer = selectedPlayers[playerOptions.player]
                Column(modifier = Modifier.fillMaxSize().horizontalPadding()) {
                    MText(
                        buildString {
                            append(playerOptions.player.player.name)
                            append(" - ")
                            append(stringResource(playerOptions.player.role.name))
                        }, style = AppTheme.typography.h2
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(top = 16.dp)
                    ) {
                        items(playerOptions.availableTargets, key = { it.player.id }) { player ->
                            MToggleListItem(
                                selected = selectedPlayer == player,
                                onClick = { selectedPlayers[playerOptions.player] = player },
                                text = { MText(player.player.name) },
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().allPadding()
            ) {
                MOutlinedButton(
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    },
                    modifier = Modifier.height(48.dp),
                    enabled = pagerState.canScrollBackward
                ) {
                    MIcon(
                        imageVector = EvaIcons.Outline.ArrowIosBack,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    MText(
                        stringResource(Resources.strings.back),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                MButton(
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    },
                    modifier = Modifier.height(48.dp),
                    enabled = selectedPlayers[options[pagerState.currentPage].player] != null
                ) {
//                    AnimatedContent(isLastItem) {
//                        if (it) {
//                            MText(stringResource(Resources.strings.start))
//                        } else {
                    MText(stringResource(Resources.strings.next))
//                        }
//                    }
                    MIcon(
                        imageVector = EvaIcons.Outline.ArrowIosForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 4.dp).size(20.dp)
                    )
                }
            }
        }
    }
}