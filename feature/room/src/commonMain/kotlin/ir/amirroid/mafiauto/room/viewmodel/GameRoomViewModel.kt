package ir.amirroid.mafiauto.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetStatusCheckCountUseCase
import ir.amirroid.mafiauto.domain.usecase.game.KickPlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.game.OnStatusCheckedUseCase
import ir.amirroid.mafiauto.domain.usecase.game.StartGameUseCase
import ir.amirroid.mafiauto.domain.usecase.game.UnKickPlayerUseCase
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
    startGameUseCase: StartGameUseCase,
    private val getAllInRoomPlayersUseCase: GetAllInRoomPlayersUseCase,
    private val getStatusCheckCountUseCase: GetStatusCheckCountUseCase,
    private val onStatusCheckedUseCase: OnStatusCheckedUseCase,
    private val kickPlayerUseCase: KickPlayerUseCase,
    private val unKickPlayerUseCase: UnKickPlayerUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(GameRoomScreenState())
    val state: StateFlow<GameRoomScreenState> = _state

    init {
        startGameUseCase.invoke()
        observeStatusCheckCount()
        observeInRoomPlayers()
    }

    private fun observeInRoomPlayers() = viewModelScope.launch(coroutineDispatcher) {
        getAllInRoomPlayersUseCase().collect { players ->
            _state.update {
                it.copy(players = players.map { player -> player.toUiModel() })
            }
        }
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

    fun kick(playerId: Long) = kickPlayerUseCase.invoke(playerId)
    fun unKick(playerId: Long) = unKickPlayerUseCase.invoke(playerId)
}