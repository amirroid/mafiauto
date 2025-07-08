package ir.amirroid.mafiauto.final_debate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleFinalTrustVotesUseCase
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.collections.immutable.toImmutableList
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

    val inFinalDebatePlayerWithRoles = getAllInRoomPlayersUseCase()
        .map { players -> players.filter { it.player.isInGame } }
        .onEach { domainPlayers = it }
        .map { players -> players.map { it.toUiModel() }.toImmutableList() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyImmutableList()
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