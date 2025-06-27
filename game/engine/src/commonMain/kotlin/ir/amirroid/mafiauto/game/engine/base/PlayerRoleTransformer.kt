package ir.amirroid.mafiauto.game.engine.base

import ir.amirroid.mafiauto.game.engine.models.Player

interface PlayerRoleTransformer : PlayerTransformer {
    fun replacePlayerRoles(
        players: List<Player>,
        from: Player,
        to: Player,
    ): List<Player> {
        val updatedPlayers = updatePlayer(
            players, from.id
        ) {
            copy(
                role = to.role,
                remainingAbilityUses = to.remainingAbilityUses,
            )
        }
        return updatePlayer(
            updatedPlayers, to.id
        ) {
            copy(role = from.role, remainingAbilityUses = from.remainingAbilityUses)
        }
    }
}