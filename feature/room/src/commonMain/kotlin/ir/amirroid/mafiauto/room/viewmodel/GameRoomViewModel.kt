package ir.amirroid.mafiauto.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.LastCardDescriptor
import ir.amirroid.mafiauto.domain.model.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.ApplyLastCardUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetAllLastCardsUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentDayUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPlayerTurnIndexUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetDefenseCandidatesUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetMessagesUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetStatusCheckCountUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GoToNextPhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleDefenseVoteResultUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleFatePhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.KickPlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.game.OnStatusCheckedUseCase
import ir.amirroid.mafiauto.domain.usecase.game.StartDefendingUseCase
import ir.amirroid.mafiauto.domain.usecase.game.StartGameUseCase
import ir.amirroid.mafiauto.domain.usecase.game.UnKickPlayerUseCase
import ir.amirroid.mafiauto.domain.usecase.game.UndoStatusCheckUseCase
import ir.amirroid.mafiauto.room.state.GameRoomScreenState
import ir.amirroid.mafiauto.ui_models.last_card.LastCardUiModel
import ir.amirroid.mafiauto.ui_models.last_card.toUiModel
import ir.amirroid.mafiauto.ui_models.phase.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.toUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameRoomViewModel(
    startGameUseCase: StartGameUseCase,
    getMessagesUseCase: GetMessagesUseCase,
    private val getAllInRoomPlayersUseCase: GetAllInRoomPlayersUseCase,
    private val getStatusCheckCountUseCase: GetStatusCheckCountUseCase,
    private val onStatusCheckedUseCase: OnStatusCheckedUseCase,
    private val onUndoStatusCheckUseCase: UndoStatusCheckUseCase,
    private val kickPlayerUseCase: KickPlayerUseCase,
    private val unKickPlayerUseCase: UnKickPlayerUseCase,
    private val getCurrentPhaseUseCase: GetCurrentPhaseUseCase,
    private val goToNextPhaseUseCase: GoToNextPhaseUseCase,
    private val getDefenseCandidatesUseCase: GetDefenseCandidatesUseCase,
    private val startDefendingUseCase: StartDefendingUseCase,
    private val handleDefenseVoteResultUseCase: HandleDefenseVoteResultUseCase,
    private val getAllLastCardsUseCase: GetAllLastCardsUseCase,
    private val applyLastCardUseCase: ApplyLastCardUseCase,
    private val handleFatePhaseUseCase: HandleFatePhaseUseCase,
    private val getCurrentDayUseCase: GetCurrentDayUseCase,
    private val getCurrentPlayerTurnIndexUseCase: GetCurrentPlayerTurnIndexUseCase,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _state = MutableStateFlow(GameRoomScreenState())
    val state: StateFlow<GameRoomScreenState> = _state

    val messages = getMessagesUseCase()

    private var domainPlayers = emptyList<PlayerWithRole>()
    private var domainLastCards = emptyList<LastCardDescriptor>()

    init {
        startGameUseCase.invoke()
        observeStatusCheckCount()
        observeInRoomPlayers()
        observeCurrentPhase()
        observeLastCards()
        observeCurrentDay()
        observeCurrentTurn()
    }

    private fun observeCurrentTurn() = viewModelScope.launch(coroutineDispatcher) {
        getCurrentPlayerTurnIndexUseCase().collect { turn ->
            _state.update {
                it.copy(currentTurn = turn)
            }
        }
    }


    private fun observeCurrentDay() = viewModelScope.launch(coroutineDispatcher) {
        getCurrentDayUseCase().collect { day ->
            _state.update {
                it.copy(currentDay = day)
            }
        }
    }

    private fun observeLastCards() = viewModelScope.launch(coroutineDispatcher) {
        getAllLastCardsUseCase().collect { cards ->
            domainLastCards = cards
            _state.update {
                it.copy(lastCards = cards.map { card -> card.toUiModel() }.toImmutableList())
            }
        }
    }

    private fun observeInRoomPlayers() = viewModelScope.launch(coroutineDispatcher) {
        getAllInRoomPlayersUseCase().collect { players ->
            domainPlayers = players
            _state.update {
                it.copy(players = players.map { player -> player.toUiModel() }.toImmutableList())
            }
        }
    }

    private fun observeCurrentPhase() = viewModelScope.launch(coroutineDispatcher) {
        getCurrentPhaseUseCase().collect { phase ->
            _state.update {
                it.copy(currentPhase = phase.toUiModel())
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

    fun increaseStatusCheck() {
        _state.update { it.copy(showStatus = true) }
        onStatusCheckedUseCase()
    }

    fun decreaseStatusCheck() = onUndoStatusCheckUseCase()

    fun hideShowStatus() {
        _state.update { it.copy(showStatus = false) }
    }

    fun pickPlayerToShowRole(playerWithRole: PlayerWithRoleUiModel) {
        _state.update { it.copy(pickedPlayerToShowRole = playerWithRole) }
    }

    fun clearPickedPlayer() {
        _state.update { it.copy(pickedPlayerToShowRole = null) }
    }

    fun kick(playerId: Long) = kickPlayerUseCase.invoke(playerId)
    fun unKick(playerId: Long) = unKickPlayerUseCase.invoke(playerId)

    fun nextPhase() = goToNextPhaseUseCase.invoke()

    fun startDefending(playerVotes: Map<PlayerWithRoleUiModel, Int>): Boolean {
        val domainPlayerVotes = playerVotes.mapKeys { (player, _) -> player.toDomain() }
        val defenders = getDefenseCandidatesUseCase(domainPlayerVotes)
        if (defenders.isNotEmpty()) {
            startDefendingUseCase(defenders)
            return false
        }
        return true
    }

    fun submitDefense(playerVotes: Map<PlayerWithRoleUiModel, Int>) =
        viewModelScope.launch(coroutineDispatcher) {
            // Delay for dismiss dialog
            delay(200)
            val domainPlayerVotes = playerVotes.mapKeys { (player, _) -> player.toDomain() }
            handleDefenseVoteResultUseCase.invoke(domainPlayerVotes)
        }


    fun applyLastCard(card: LastCardUiModel, pickedPlayers: List<PlayerWithRoleUiModel>) {
        applyLastCardUseCase.invoke(card.toDomain(), pickedPlayers.map { it.toDomain() })
    }

    fun handleFate() = handleFatePhaseUseCase()

    private fun PlayerWithRoleUiModel.toDomain() = domainPlayers.first { it.player.id == player.id }
    private fun LastCardUiModel.toDomain() = domainLastCards.first { it.key == key }
}