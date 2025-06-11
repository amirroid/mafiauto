package ir.amirroid.mafiauto.domain.usecase.player_role

import ir.amirroid.mafiauto.domain.repository.PlayerRoleRepository

class GetAllPlayersWithRolesUseCase(
    private val playerRoleRepository: PlayerRoleRepository
) {
    operator fun invoke() = playerRoleRepository.getAllSavedPlayersWithRoles()
}