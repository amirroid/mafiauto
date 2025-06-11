package ir.amirroid.mafiauto.reveal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.player_role.GetPlayerWithRolesAndSaveUseCase
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel

class RevealRolesViewModel(
    getPlayerWithRolesAndSaveUseCase: GetPlayerWithRolesAndSaveUseCase
) : ViewModel() {
    val playerWithRoles = getPlayerWithRolesAndSaveUseCase().map { it.toUiModel() }

    var currentPlayerIndex by mutableIntStateOf(0)
}