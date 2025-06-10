package ir.amirroid.mafiauto.domain.usecase.player

import ir.amirroid.mafiauto.domain.repository.PlayerRepository

class RemovePlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(id: Long) = playerRepository.deletePlayer(id)
}