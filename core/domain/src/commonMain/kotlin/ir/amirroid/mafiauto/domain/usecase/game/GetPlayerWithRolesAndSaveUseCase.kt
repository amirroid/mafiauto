package ir.amirroid.mafiauto.domain.usecase.game

import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.GameRepository

class GetPlayerWithRolesAndSaveUseCase(
    private val gameRepository: GameRepository
) {
    operator fun invoke(): List<PlayerWithRole> {
        val playersWithRoles = gameRepository.assignRoles()
        gameRepository.savePlayersWithRoles(playersWithRoles)
        return playersWithRoles
    }
}