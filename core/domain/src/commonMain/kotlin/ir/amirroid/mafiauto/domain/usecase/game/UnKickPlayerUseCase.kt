package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class UnKickPlayerUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(playerId: Long) = gameRepository.unKickPlayer(playerId)
}