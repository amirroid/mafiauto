package ir.amirroid.mafiauto.night.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.NightActionDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleNightActionsUseCase
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.phase.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NightActionsViewModel(
    getCurrentPhaseUseCase: GetCurrentPhaseUseCase,
    private val getAllInRoomPlayersUseCase: GetAllInRoomPlayersUseCase,
    private val handleNightActionsUseCase: HandleNightActionsUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    val currentPhase = getCurrentPhaseUseCase.invoke()
        .map { it.toUiModel() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            GamePhaseUiModel.Night(options = emptyList())
        )

    private var domainPlayers: List<PlayerWithRole>? = null

    private val _selectedPlayers =
        MutableStateFlow(emptyMap<PlayerWithRoleUiModel, PlayerWithRoleUiModel>())
    val selectedPlayers = _selectedPlayers.asStateFlow()


    fun togglePlayer(player: PlayerWithRoleUiModel, target: PlayerWithRoleUiModel) {
        _selectedPlayers.update {
            it.toMutableMap().apply { put(player, target) }.toMap()
        }
    }

    fun applyActions() = viewModelScope.launch(coroutineDispatcher) {
        handleNightActionsUseCase.invoke(
            _selectedPlayers.value.map { (player, target) ->
                NightActionDescriptor(player = player.toDomain(), target = target.toDomain())
            })
    }

    private suspend fun PlayerWithRoleUiModel.toDomain(): PlayerWithRole {
        if (domainPlayers == null) domainPlayers = getAllInRoomPlayersUseCase().first()
        return domainPlayers!!.first { it.player.id == player.id }
    }
}