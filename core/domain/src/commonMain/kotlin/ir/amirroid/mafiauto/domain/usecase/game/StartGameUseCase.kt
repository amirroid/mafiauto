package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.repository.GameRepository
import ir.amirroid.mafiauto.domain.repository.PlayerRoleRepository

class StartGameUseCase(
    private val gameRepository: GameRepository,
    private val playerRoleRepository: PlayerRoleRepository
) {
    operator fun invoke() =
        gameRepository.startNewGame(players = playerRoleRepository.getAllSavedPlayersWithRoles())
}