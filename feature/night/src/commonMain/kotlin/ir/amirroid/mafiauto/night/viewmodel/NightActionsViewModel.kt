package ir.amirroid.mafiauto.night.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPhaseUseCase
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.phase.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NightActionsViewModel(
    getCurrentPhaseUseCase: GetCurrentPhaseUseCase
) : ViewModel() {
    val currentPhase = getCurrentPhaseUseCase.invoke()
        .map { it.toUiModel() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            GamePhaseUiModel.Night(options = emptyList())
        )

    private val _selectedPlayers =
        MutableStateFlow(emptyMap<PlayerWithRoleUiModel, PlayerWithRoleUiModel>())
    val selectedPlayers = _selectedPlayers.asStateFlow()


    fun togglePlayer(player: PlayerWithRoleUiModel, target: PlayerWithRoleUiModel) {
        _selectedPlayers.update {
            it.toMutableMap().apply { put(player, target) }.toMap()
        }
    }
}