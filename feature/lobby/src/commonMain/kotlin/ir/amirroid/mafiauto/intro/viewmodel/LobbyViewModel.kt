package ir.amirroid.mafiauto.intro.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.player.Player
import ir.amirroid.mafiauto.domain.usecase.groups.EditGroupNameUseCase
import ir.amirroid.mafiauto.domain.usecase.groups.GetGroupUseCase
import ir.amirroid.mafiauto.domain.usecase.groups.UpdateGroupPinStateUseCase
import ir.amirroid.mafiauto.domain.usecase.player.AddPlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.player.GetAllPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.player.RemovePlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.player.SelectNewPlayersUseCase
import ir.amirroid.mafiauto.intro.state.LobbyScreenState
import ir.amirroid.mafiauto.ui_models.group.toUiModel
import ir.amirroid.mafiauto.ui_models.player.PlayerUiModel
import ir.amirroid.mafiauto.ui_models.player.toUiModel
import kotlinx.collections.immutable.toImmutableList
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
    private val editGroupNameUseCase: EditGroupNameUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val getGroupUseCase: GetGroupUseCase,
    private val updateGroupPinStateUseCase: UpdateGroupPinStateUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(LobbyScreenState())
    val state = _state.asStateFlow()

    private var playerDomains = emptyList<Player>()

    private val groupId = savedStateHandle.get<Long>("groupId")!!

    init {
        observePlayers()
        observeToGroup()
    }

    private fun observeToGroup() = viewModelScope.launch(dispatcher) {
        getGroupUseCase.invoke(groupId).distinctUntilChanged().collectLatest { newGroup ->
            _state.update {
                it.copy(group = newGroup.toUiModel())
            }
        }
    }

    private fun observePlayers() = viewModelScope.launch(dispatcher) {
        getAllPlayersUseCase(groupId).distinctUntilChanged().collectLatest { players ->
            val playerDomainsIds = playerDomains.map { it.id }
            val playerUiModels = players.toUiModels()
            _state.update {
                val newSelectedPlayers =
                    it.selectedPlayers + playerUiModels.filter { player -> player.id !in playerDomainsIds }
                it.copy(
                    players = playerUiModels.toImmutableList(),
                    selectedPlayers = newSelectedPlayers.toImmutableList()
                )
            }
            playerDomains = players
        }
    }

    fun togglePlayerSelection(player: PlayerUiModel) {
        _state.update { current ->
            val updatedSelection = current.selectedPlayers.toMutableSet().apply {
                if (!add(player)) remove(player)
            }.toList()
            current.copy(selectedPlayers = updatedSelection.toImmutableList())
        }
    }

    fun updateNewPlayerName(name: String) {
        _state.update { it.copy(newPlayerName = name) }
    }

    fun addNewPlayer() = viewModelScope.launch(dispatcher) {
        val name = state.value.newPlayerName.trim()
        if (name.isEmpty()) return@launch

        addPlayerUseCase(name, groupId)
        updateNewPlayerName("")
    }

    fun deletePlayer(player: PlayerUiModel) = viewModelScope.launch(dispatcher) {
        removePlayerUseCase.invoke(player.id)
        _state.update { current ->
            val updatedSelection = current.selectedPlayers.toMutableSet().apply {
                if (contains(player)) remove(player)
            }.toList()
            current.copy(selectedPlayers = updatedSelection.toImmutableList())
        }
    }

    fun selectPlayers() {
        selectNewPlayersUseCase.invoke(state.value.selectedPlayers.toDomains())
    }

    fun openEditing() {
        _state.update { it.copy(isEditing = true) }
    }

    fun closeEditing() {
        _state.update { it.copy(isEditing = false) }
    }

    fun updateGroupName(newName: String) = viewModelScope.launch(dispatcher) {
        editGroupNameUseCase.invoke(groupId, newName)
    }

    fun togglePinGroup() = viewModelScope.launch(dispatcher) {
        updateGroupPinStateUseCase.invoke(groupId, isPinned = state.value.group.isPinned.not())
    }

    private fun List<Player>.toUiModels() = map { it.toUiModel() }
    private fun List<PlayerUiModel>.toDomains() = map { uiModel ->
        playerDomains.first { it.id == uiModel.id }
    }
}