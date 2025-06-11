package ir.amirroid.mafiauto.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.game.GetStatusCheckCountUseCase
import ir.amirroid.mafiauto.domain.usecase.game.OnStatusCheckedUseCase
import ir.amirroid.mafiauto.domain.usecase.player_role.GetAllPlayersWithRolesUseCase
import ir.amirroid.mafiauto.room.state.GameRoomScreenState
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameRoomViewModel(
    getAllPlayersWithRolesUseCase: GetAllPlayersWithRolesUseCase,
    private val getStatusCheckCountUseCase: GetStatusCheckCountUseCase,
    private val onStatusCheckedUseCase: OnStatusCheckedUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val players = getAllPlayersWithRolesUseCase().map { it.toUiModel() }

    private val _state = MutableStateFlow(GameRoomScreenState(players = players))
    val state: StateFlow<GameRoomScreenState> = _state

    init {
        observeStatusCheckCount()
    }

    private fun observeStatusCheckCount() = viewModelScope.launch(coroutineDispatcher) {
        getStatusCheckCountUseCase().collect { count ->
            _state.update {
                it.copy(statusChecksCount = count)
            }
        }
    }

    fun addStatusCheck() = onStatusCheckedUseCase()

    fun pickPlayerToShowRole(playerWithRole: PlayerWithRoleUiModel) {
        _state.update { it.copy(pickedPlayerToShowRole = playerWithRole) }
    }

    fun clearPickedPlayer() {
        _state.update { it.copy(pickedPlayerToShowRole = null) }
    }
}