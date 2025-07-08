package ir.amirroid.mafiauto.reveal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.player_role.GetPlayerWithRolesAndSaveUseCase
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.collections.immutable.toImmutableList

class RevealRolesViewModel(
    getPlayerWithRolesAndSaveUseCase: GetPlayerWithRolesAndSaveUseCase
) : ViewModel() {
    val playerWithRoles =
        getPlayerWithRolesAndSaveUseCase().map { it.toUiModel() }.toImmutableList()

    var currentPlayerIndex by mutableIntStateOf(0)
    var showPlayersRole by mutableStateOf(true)

    fun nextPlayer() {
        showPlayersRole = if (showPlayersRole) {
            false
        } else {
            currentPlayerIndex =
                currentPlayerIndex.plus(1).coerceAtMost(playerWithRoles.size.minus(1))
            true
        }
    }

    fun previewsPlayer() {
        showPlayersRole = if (showPlayersRole) {
            false
        } else {
            currentPlayerIndex = currentPlayerIndex.minus(1).coerceAtLeast(0)
            true
        }
    }
}