package ir.amirroid.mafiauto.domain.usecase.player

import ir.amirroid.mafiauto.domain.repository.PlayerRepository

class GetSelectedPlayersUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke() = playerRepository.getAllSelectedPlayers()
}