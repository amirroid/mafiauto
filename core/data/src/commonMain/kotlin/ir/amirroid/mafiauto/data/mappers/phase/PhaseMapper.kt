package ir.amirroid.mafiauto.data.mappers.phase

import ir.amirroid.mafiauto.data.mappers.player_role.toPlayerRoleDomain
import ir.amirroid.mafiauto.domain.model.GamePhase
import ir.amirroid.mafiauto.game.engine.models.Phase

fun Phase.toDomain() = when (this) {
    is Phase.Day -> GamePhase.Day
    is Phase.Night -> GamePhase.Night
    is Phase.Voting -> GamePhase.Voting
    is Phase.Result -> GamePhase.Result
    is Phase.Defending -> GamePhase.Defending(
        defenders = defenders.map { it.toPlayerRoleDomain() }
    )
}