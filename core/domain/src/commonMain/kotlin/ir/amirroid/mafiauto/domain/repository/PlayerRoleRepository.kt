package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole

interface PlayerRoleRepository {
    fun assignRoles(): List<PlayerWithRole>
    fun savePlayersWithRoles(data: List<PlayerWithRole>)
    fun getAllSavedPlayersWithRoles(): List<PlayerWithRole>
}