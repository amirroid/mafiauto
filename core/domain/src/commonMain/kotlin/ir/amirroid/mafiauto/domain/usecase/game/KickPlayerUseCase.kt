package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class KickPlayerUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(playerId: Long) = gameRepository.kickPlayer(playerId)
}