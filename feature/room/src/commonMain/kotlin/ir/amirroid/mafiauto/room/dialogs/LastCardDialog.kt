package ir.amirroid.mafiauto.room.dialogs

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ir.amirroid.mafiauto.design_system.components.list.ListItemDefaults
import ir.amirroid.mafiauto.design_system.components.list.base.MListItem
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
    targetPlayer: PlayerWithRoleUiModel,
    onDismissRequest: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    var selectedCardIndex by remember { mutableStateOf<Int?>(null) }
    val visibleStates = remember { cards.map { mutableStateOf(false) } }

    LaunchedEffect(Unit) {
        visibleStates.forEachIndexed { index, state ->
            delay(50L * index)
            state.value = true
        }
    }

    LaunchedEffect(selectedCardIndex) {
        if (selectedCardIndex == null) return@LaunchedEffect
        delay(4000)
        visible = false
    }

    BaseBlurredPopup(
        isVisible = visible,
        onDismissRequest = onDismissRequest,
        dismissOnBackPress = false
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth(0.83f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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

            Spacer(Modifier.height(24.dp))

            cards.forEachIndexed { index, card ->
                key(card.key) {
                    AnimatedVisibility(
                        visible = visibleStates[index].value,
                        enter = fadeIn(
                            animationSpec = spring(
                                stiffness = Spring.StiffnessLow,
                                dampingRatio = Spring.DampingRatioNoBouncy
                            )
                        ) + slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow,
                                dampingRatio = Spring.DampingRatioMediumBouncy
                            )
                        )
                    ) {
                        val isSelected = selectedCardIndex == index
                        MListItem(
                            text = {
                                AnimatedContent(isSelected) { selected ->
                                    if (selected) {
                                        MText(stringResource(card.name))
                                    } else {
                                        MText(
                                            "${stringResource(Resources.strings.card)} ${
                                                index.plus(
                                                    1
                                                )
                                            }"
                                        )
                                    }
                                }
                            },
                            onClick = {
                                selectedCardIndex = index
                                visibleStates.forEachIndexed { i, state ->
                                    if (i != index) state.value = false
                                }
                            },
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
            }
        }
    }
}