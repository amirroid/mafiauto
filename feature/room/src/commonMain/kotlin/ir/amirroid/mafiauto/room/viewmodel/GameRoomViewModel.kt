package ir.amirroid.mafiauto.room.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.game.GetAllPlayersWithRolesUseCase

class GameRoomViewModel(
    private val getAllPlayersWithRolesUseCase: GetAllPlayersWithRolesUseCase
) : ViewModel() {
    val players = getAllPlayersWithRolesUseCase.invoke()
}