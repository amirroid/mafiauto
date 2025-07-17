package ir.amirroid.mafiauto.engine.repository

import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.provider.roles.RolesProvider

class FakePlayersRepository(
    private val roleProvider: RolesProvider
) {
    fun getPlayers() = roleProvider.getAllRoles().mapIndexed { index, role ->
        Player(
            id = index.plus(1L),
            name = "Player ${index.plus(1)}",
            role = role
        )
    }
}