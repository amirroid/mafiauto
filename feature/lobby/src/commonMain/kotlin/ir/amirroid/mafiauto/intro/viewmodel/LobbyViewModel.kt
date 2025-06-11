package ir.amirroid.mafiauto.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.Player
import ir.amirroid.mafiauto.domain.usecase.player.AddPlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.player.GetAllPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.player.RemovePlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.player.SelectNewPlayersUseCase
import ir.amirroid.mafiauto.intro.state.LobbyScreenState
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel
import ir.amirroid.mafiauto.ui_models.player.toDomain
import ir.amirroid.mafiauto.ui_models.player.toUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LobbyViewModel(
    private val getAllPlayersUseCase: GetAllPlayersUseCase,
    private val addPlayerUseCase: AddPlayerUseCase,
    private val removePlayerUseCase: RemovePlayerUseCase,
    private val selectNewPlayersUseCase: SelectNewPlayersUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = MutableStateFlow(LobbyScreenState())
    val state = _state.asStateFlow()

    init {
        observePlayers()
    }

    private fun observePlayers() = viewModelScope.launch(dispatcher) {
        getAllPlayersUseCase()
            .distinctUntilChanged()
            .collectLatest { players ->
                _state.update { it.copy(players = players.toUiModels()) }
            }
    }

    fun togglePlayerSelection(player: PlayerUiModel) {
        _state.update { current ->
            val updatedSelection = current.selectedPlayers.toMutableSet().apply {
                if (!add(player)) remove(player)
            }.toList()
            current.copy(selectedPlayers = updatedSelection)
        }
    }

    fun updateNewPlayerName(name: String) {
        _state.update { it.copy(newPlayerName = name) }
    }

    fun addNewPlayer() = viewModelScope.launch(dispatcher) {
        val name = state.value.newPlayerName.trim()
        if (name.isEmpty()) return@launch

        addPlayerUseCase(name)
        updateNewPlayerName("")
    }

    fun deletePlayer(player: PlayerUiModel) = viewModelScope.launch(dispatcher) {
        removePlayerUseCase.invoke(player.id)
        _state.update { current ->
            val updatedSelection = current.selectedPlayers.toMutableSet().apply {
                if (contains(player)) remove(player)
            }.toList()
            current.copy(selectedPlayers = updatedSelection)
        }
    }

    fun selectPlayers() {
        selectNewPlayersUseCase.invoke(state.value.selectedPlayers.toDomains())
    }

    private fun List<Player>.toUiModels() = map { it.toUiModel() }
    private fun List<PlayerUiModel>.toDomains() = map { it.toDomain() }
}