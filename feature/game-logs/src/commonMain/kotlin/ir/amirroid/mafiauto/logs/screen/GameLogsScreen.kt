package ir.amirroid.mafiauto.logs.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.amirroid.mafiauto.common.compose.components.BackButton
import ir.amirroid.mafiauto.common.compose.extra.defaultVerticalContentPadding
import ir.amirroid.mafiauto.common.compose.modifiers.horizontalPadding
import ir.amirroid.mafiauto.common.compose.operators.plus
import ir.amirroid.mafiauto.design_system.components.appbar.SmallTopAppBarScaffold
import ir.amirroid.mafiauto.design_system.components.text.MText
import ir.amirroid.mafiauto.logs.viewmodel.GameLogsViewModel
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.theme.core.AppTheme
import ir.amirroid.mafiauto.ui_models.log.GameLogUiModel
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun GameLogsScreen(
    onBack: () -> Unit, viewModel: GameLogsViewModel = koinViewModel()
) {
    val logs by viewModel.logs.collectAsStateWithLifecycle()

    SmallTopAppBarScaffold(
        title = { MText(stringResource(Resources.strings.gameLogs)) },
        navigationIcon = { BackButton(onBack = onBack) },
    ) { paddingValues ->
        LogsList(logs, contentPadding = defaultVerticalContentPadding() + paddingValues)
    }
}

@Composable
private fun LogsList(
    logs: ImmutableList<GameLogUiModel>, contentPadding: PaddingValues = PaddingValues()
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = contentPadding
    ) {
        itemsIndexed(logs, key = { i, _ -> i }) { index, log ->
            LogItem(log = log, isLastItem = index == logs.size - 1)
        }
    }
}

@Composable
private fun LogItem(log: GameLogUiModel, isLastItem: Boolean) {
    when (log) {
        is GameLogUiModel.Kick -> {
            KickLogSection(log, isLastItem)
        }

        is GameLogUiModel.NightActions -> {
            NightActionsLogSection(log, isLastItem)
        }

        is GameLogUiModel.ApplyLastCard -> {
            ApplyLastCardLogSection(log, isLastItem)
        }

        is GameLogUiModel.DefenseVotes -> {
            DefenseVotesLogSection(log, isLastItem)
        }

        is GameLogUiModel.ElectedInDefense -> {
            ElectedInDefenseLogSection(log, isLastItem)
        }

        is GameLogUiModel.FinalDebate -> {
            FinalDebateLogSection(log, isLastItem)
        }
    }
}


@Composable
private fun LogSection(
    title: String, relatedDay: Int, isLastItem: Boolean, content: @Composable () -> Unit
) {
    val primaryColor = AppTheme.colorScheme.primary
    Column(modifier = Modifier.fillMaxWidth().drawBehind {
            if (!isLastItem) {
                drawLine(
                    primaryColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }.horizontalPadding().padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MText(buildAnnotatedString {
            append(title)
            append(" - ")
            withStyle(SpanStyle(color = primaryColor)) {
                append("${stringResource(Resources.strings.day)} $relatedDay")
            }
        }, style = AppTheme.typography.h3)
        content()
    }
}

@Composable
private fun KickLogSection(log: GameLogUiModel.Kick, isLastItem: Boolean) {
    LogSection(
        title = if (log.isKicked) stringResource(Resources.strings.kick) else stringResource(
            Resources.strings.unKick
        ), relatedDay = log.day, isLastItem = isLastItem
    ) {
        MText(log.player.player.name)
    }
}

@Composable
private fun NightActionsLogSection(log: GameLogUiModel.NightActions, isLastItem: Boolean) {
    LogSection(
        title = stringResource(Resources.strings.nightActions),
        isLastItem = isLastItem,
        relatedDay = log.day,
    ) {
        Column {
            log.actions.forEach { (player, targets, replacementRole) ->
                MText(
                    buildString {
                        append("• ")
                        append(player.player.name)
                        append(" ${stringResource(replacementRole?.name ?: player.role.name)} ")
                        append("→ ")
                        if (targets.size == 1) {
                            append(targets.first().player.name)
                        } else {
                            append(
                                targets.joinToString(
                                    ", ", prefix = "(", postfix = ")"
                                ) { it.player.name })
                        }
                    })
            }
        }
    }
}

@Composable
private fun ApplyLastCardLogSection(log: GameLogUiModel.ApplyLastCard, isLastItem: Boolean) {
    LogSection(
        title = stringResource(Resources.strings.lastCard),
        isLastItem = isLastItem,
        relatedDay = log.day,
    ) {
        MText(
            text = buildString {
                appendLine("${stringResource(Resources.strings.player)}: ${log.player.player.name}")
                appendLine("${stringResource(Resources.strings.card)}: ${stringResource(log.card.name)}")
                val selectedPlayers = log.selectedPlayers.joinToString(
                    ", ", prefix = "(", postfix = ")"
                ) { it.player.name }
                appendLine("${stringResource(Resources.strings.selectPlayers)}: $selectedPlayers")
            })
    }
}

@Composable
private fun DefenseVotesLogSection(log: GameLogUiModel.DefenseVotes, isLastItem: Boolean) {
    LogSection(
        title = stringResource(Resources.strings.voting),
        isLastItem = isLastItem,
        relatedDay = log.day,
    ) {
        Column {
            log.playerVotes.forEach { (player, voteCount) ->
                MText(
                    buildString {
                        append("• ")
                        append(player.player.name)
                        append(" → ")
                        append(voteCount.toString())
                    })
            }
        }
    }
}

@Composable
private fun ElectedInDefenseLogSection(log: GameLogUiModel.ElectedInDefense, isLastItem: Boolean) {
    LogSection(
        title = stringResource(Resources.strings.eliminatedInVote),
        isLastItem = isLastItem,
        relatedDay = log.day,
    ) {
        MText(log.player.player.name)
    }
}

@Composable
private fun FinalDebateLogSection(log: GameLogUiModel.FinalDebate, isLastItem: Boolean) {
    LogSection(
        title = stringResource(Resources.strings.eliminatedInVote),
        isLastItem = isLastItem,
        relatedDay = log.day,
    ) {
        Column {
            log.playersTrustVotes.forEach { (player, trustPlayer) ->
                MText(
                    buildString {
                        append("• ")
                        append(player.player.name)
                        append(" → ")
                        append(trustPlayer.player.name)
                    })
            }
        }
    }
}