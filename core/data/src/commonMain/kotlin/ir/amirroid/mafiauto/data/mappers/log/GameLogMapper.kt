package ir.amirroid.mafiauto.data.mappers.log

import ir.amirroid.mafiauto.data.mappers.action.toDomain
import ir.amirroid.mafiauto.data.mappers.last_card.toDomain
import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.domain.model.game.GameLog
import ir.amirroid.mafiauto.game.engine.log.GameLog as EngineGameLog

fun EngineGameLog.toDomain(): GameLog = when (this) {
    is EngineGameLog.NightActions -> {
        GameLog.NightActions(
            actions = actions.map { it.toDomain() },
            relatedDay = relatedDay
        )
    }

    is EngineGameLog.DefenseVotes -> {
        GameLog.DefenseVotes(
            playerVotes = playerVotes.mapKeys { it.key.toPlayerRoleDomain() },
            relatedDay = relatedDay
        )
    }

    is EngineGameLog.ElectedInDefense -> {
        GameLog.ElectedInDefense(
            player = player.toPlayerRoleDomain(),
            relatedDay = relatedDay
        )
    }

    is EngineGameLog.ApplyLastCard -> {
        GameLog.ApplyLastCard(
            player = player.toPlayerRoleDomain(),
            card = card.toDomain(),
            selectedPlayers = selectedPlayers.map { it.toPlayerRoleDomain() },
            relatedDay = relatedDay
        )
    }

    is EngineGameLog.Kick -> {
        GameLog.Kick(
            player = player.toPlayerRoleDomain(),
            isKicked = isKicked,
            relatedDay = relatedDay
        )
    }

    is EngineGameLog.FinalDebate -> {
        GameLog.FinalDebate(
            playersTrustVotes = playersTrustVotes
                .entries
                .associate { (key, value) ->
                    key.toPlayerRoleDomain() to value.toPlayerRoleDomain()
                },
            relatedDay = relatedDay
        )
    }
}