package ir.amirroid.mafiauto.room.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.game.GetAllPlayersWithRolesUseCase
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel

class GameRoomViewModel(
    getAllPlayersWithRolesUseCase: GetAllPlayersWithRolesUseCase
) : ViewModel() {
    val players = getAllPlayersWithRolesUseCase.invoke().map { it.toUiModel() }

    var pickedPlayerToShowRole by mutableStateOf<PlayerWithRoleUiModel?>(null)
}