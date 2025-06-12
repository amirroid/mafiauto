package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository

class GetAllInRoomPlayersUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = gameRepository.getAllPlayers()
}