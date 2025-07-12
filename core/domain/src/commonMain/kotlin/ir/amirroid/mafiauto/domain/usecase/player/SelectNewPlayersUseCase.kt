package ir.amirroid.mafiauto.domain.usecase.player

import ir.amirroid.mafiauto.domain.model.player.Player
import ir.amirroid.mafiauto.domain.repository.PlayerRepository

class SelectNewPlayersUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(newPlayers: List<Player>) = playerRepository.selectPlayers(newPlayers)
}