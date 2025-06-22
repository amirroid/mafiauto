package ir.amirroid.mafiauto.night.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.model.InstantAction
import ir.amirroid.mafiauto.domain.model.NightActionDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleNightActionsUseCase
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources
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
import org.jetbrains.compose.resources.StringResource

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
        MutableStateFlow(emptyMap<PlayerWithRoleUiModel, List<PlayerWithRoleUiModel>>())
    val selectedPlayers = _selectedPlayers.asStateFlow()

    val disablePlayerIdSelections = mutableStateListOf<Long>()

    fun togglePlayer(player: PlayerWithRoleUiModel, target: PlayerWithRoleUiModel) {
        _selectedPlayers.update {
            it.toMutableMap().apply {
                val playerTargets = get(player)
                if (playerTargets?.contains(target) == true) {
                    put(player, playerTargets.filter { p -> p.player.id != target.player.id })
                } else {
                    put(player, (playerTargets ?: emptyList()) + target)
                }
            }
        }
    }

    fun applyActions() = viewModelScope.launch(coroutineDispatcher) {
        handleNightActionsUseCase.invoke(
            _selectedPlayers.value.map { (player, target) ->
                NightActionDescriptor(player = player.toDomain(), targets = target.toDomains())
            })
    }

    private suspend fun PlayerWithRoleUiModel.toDomain(): PlayerWithRole {
        if (domainPlayers == null) domainPlayers = getAllInRoomPlayersUseCase().first()
        return domainPlayers!!.first { it.player.id == player.id }
    }

    private suspend fun List<PlayerWithRoleUiModel>.toDomains() = map { it.toDomain() }


    fun handleInstantAction(
        action: InstantAction,
        currentPlayerRole: PlayerWithRoleUiModel,
        selectedPlayerRoles: List<PlayerWithRoleUiModel>?,
        onShowSnakeBar: (StringResource) -> Unit,
        onDisablePlayer: (Long) -> Unit
    ) {
        when (action) {
            InstantAction.SHOW_ALIGNMENT -> {
                val selectedPlayerRole = selectedPlayerRoles?.firstOrNull() ?: return
                if (selectedPlayerRole.role.alignment == Alignment.Mafia && selectedPlayerRole.role.key != RoleKeys.GOD_FATHER) {
                    onShowSnakeBar.invoke(Resources.strings.correctGuess)
                } else {
                    onShowSnakeBar.invoke(Resources.strings.wrongGuess)
                }
                onDisablePlayer.invoke(currentPlayerRole.player.id)
            }

            InstantAction.SHOW_ALIGNMENTS_COUNT -> {
                // show role counts
                onDisablePlayer.invoke(currentPlayerRole.player.id)
            }
        }
    }
}