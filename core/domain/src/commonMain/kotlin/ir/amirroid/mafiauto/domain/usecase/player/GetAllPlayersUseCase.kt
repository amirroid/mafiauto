package ir.amirroid.mafiauto.domain.usecase.player

import ir.amirroid.mafiauto.domain.repository.PlayerRepository

class GetAllPlayersUseCase(
    private val playerRepository: PlayerRepository
) {
    operator fun invoke(groupId: Long) = playerRepository.getAllPlayers(groupId)
}