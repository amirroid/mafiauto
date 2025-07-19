package ir.amirroid.mafiauto.ui_models.log

import ir.amirroid.mafiauto.domain.model.game.GameLog
import ir.amirroid.mafiauto.ui_models.last_card.toUiModel
import ir.amirroid.mafiauto.ui_models.night_action.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

fun GameLog.toUiModel(): GameLogUiModel = when (this) {
    is GameLog.NightActions -> {
        GameLogUiModel.NightActions(
            actions = actions.map { it.toUiModel() }.toImmutableList(),
            relatedDay = relatedDay
        )
    }

    is GameLog.DefenseVotes -> {
        GameLogUiModel.DefenseVotes(
            playerVotes = playerVotes.mapKeys { it.key.toUiModel() }.toImmutableMap(),
            relatedDay = relatedDay
        )
    }

    is GameLog.ElectedInDefense -> {
        GameLogUiModel.ElectedInDefense(
            player = player.toUiModel(),
            relatedDay = relatedDay
        )
    }

    is GameLog.ApplyLastCard -> {
        GameLogUiModel.ApplyLastCard(
            player = player.toUiModel(),
            card = card.toUiModel(),
            selectedPlayers = selectedPlayers.map { it.toUiModel() }.toImmutableList(),
            relatedDay = relatedDay
        )
    }

    is GameLog.Kick -> {
        GameLogUiModel.Kick(
            player = player.toUiModel(),
            isKicked = isKicked,
            relatedDay = relatedDay
        )
    }

    is GameLog.FinalDebate -> {
        GameLogUiModel.FinalDebate(
            playersTrustVotes = playersTrustVotes
                .entries
                .associate { (key, value) ->
                    key.toUiModel() to value.toUiModel()
                },
            relatedDay = relatedDay
        )
    }
}