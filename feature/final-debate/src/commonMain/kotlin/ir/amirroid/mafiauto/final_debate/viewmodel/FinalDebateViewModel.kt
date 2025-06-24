package ir.amirroid.mafiauto.final_debate.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleFinalTrustVotesUseCase
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class FinalDebateViewModel(
    getAllInRoomPlayersUseCase: GetAllInRoomPlayersUseCase,
    private val handleFinalTrustVotesUseCase: HandleFinalTrustVotesUseCase
) : ViewModel() {
    private var domainPlayers: List<PlayerWithRole>? = null

    val players = getAllInRoomPlayersUseCase()
        .onEach { domainPlayers = it }
        .map { players -> players.map { it.toUiModel() } }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    private val _selectedPlayers =
        MutableStateFlow(emptyMap<PlayerWithRoleUiModel, PlayerWithRoleUiModel>())
    val selectedPlayers = _selectedPlayers.asStateFlow()


    fun togglePlayer(player: PlayerWithRoleUiModel, target: PlayerWithRoleUiModel) {
        _selectedPlayers.update { current ->
            if (player in current) {
                current - player
            } else {
                current + (player to target)
            }
        }
    }

    fun completeDebate() {
        val convertedVotes = selectedPlayers.value.map { (voter, voted) ->
            voter.toDomain() to voted.toDomain()
        }.toMap()
        handleFinalTrustVotesUseCase.invoke(convertedVotes)
    }

    private fun PlayerWithRoleUiModel.toDomain(): PlayerWithRole {
        return domainPlayers!!.first { it.player.id == player.id }
    }
}