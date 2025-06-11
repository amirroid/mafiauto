package ir.amirroid.mafiauto.domain.usecase.player_role

import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.PlayerRoleRepository

class GetPlayerWithRolesAndSaveUseCase(
    private val playerRoleRepository: PlayerRoleRepository
) {
    operator fun invoke(): List<PlayerWithRole> {
        val playersWithRoles = playerRoleRepository.assignRoles()
        playerRoleRepository.savePlayersWithRoles(playersWithRoles)
        return playersWithRoles
    }
}