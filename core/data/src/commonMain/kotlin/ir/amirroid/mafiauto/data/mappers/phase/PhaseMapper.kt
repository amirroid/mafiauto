package ir.amirroid.mafiauto.data.mappers.phase

import ir.amirroid.mafiauto.data.mappers.night_target_options.toDomain
import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.game.engine.models.Phase

fun Phase.toDomain() = when (this) {
    is Phase.Day -> GamePhase.Day
    is Phase.Night -> GamePhase.Night(options = options.map { it.toDomain() })
    is Phase.Voting -> GamePhase.Voting
    is Phase.Fate -> GamePhase.Fate(targetPlayer = targetPlayer.toPlayerRoleDomain())
    is Phase.LastCard -> GamePhase.LastCard(player = player.toPlayerRoleDomain())
    is Phase.Result -> GamePhase.Result
    is Phase.Defending -> GamePhase.Defending(
        defenders = defenders.map { it.toPlayerRoleDomain() }
    )
}