package ir.amirroid.mafiauto.domain.usecase.player

import ir.amirroid.mafiauto.domain.repository.PlayerRepository

class AddPlayerUseCase(
    private val playerRepository: PlayerRepository
) {
    suspend operator fun invoke(name: String) = playerRepository.addPlayer(name)
}