package ir.amirroid.mafiauto.reveal.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.game.GetPlayerWithRolesAndSaveUseCase
import ir.amirroid.mafiauto.reveal.mappers.toUiModel

class RevealRolesViewModel(
    getPlayerWithRolesAndSaveUseCase: GetPlayerWithRolesAndSaveUseCase
) : ViewModel() {
    val playerWithRoles = getPlayerWithRolesAndSaveUseCase().map { it.toUiModel() }

    var currentPlayerIndex by mutableIntStateOf(0)
}