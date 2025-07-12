package ir.amirroid.mafiauto.night.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.domain.model.role.Alignment
import ir.amirroid.mafiauto.domain.model.game.InstantAction
import ir.amirroid.mafiauto.domain.model.game.NightActionDescriptor
import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.usecase.game.GetAllInRoomPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.game.GetCurrentPhaseUseCase
import ir.amirroid.mafiauto.domain.usecase.game.HandleNightActionsUseCase
import ir.amirroid.mafiauto.domain.usecase.role.GetSingleRoleDescriptorUseCase
import ir.amirroid.mafiauto.game.engine.utils.RegexUtils
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources
import ir.amirroid.mafiauto.ui_models.night_target_otpions.NightTargetOptionsUiModel
import ir.amirroid.mafiauto.ui_models.phase.GamePhaseUiModel
import ir.amirroid.mafiauto.ui_models.phase.toUiModel
import ir.amirroid.mafiauto.ui_models.player_with_role.PlayerWithRoleUiModel
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
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
    private val getSingleRoleDescriptorUseCase: GetSingleRoleDescriptorUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {
    val currentPhase = getCurrentPhaseUseCase.invoke()
        .map { it.toUiModel() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            GamePhaseUiModel.Night(options = emptyImmutableList())
        )

    private var domainPlayers: List<PlayerWithRole>? = null

    private val _selectedPlayers =
        MutableStateFlow(emptyMap<PlayerWithRoleUiModel, ImmutableList<PlayerWithRoleUiModel>>())
    val selectedPlayers = _selectedPlayers.asStateFlow()

    val disableActionKeysSelections = mutableStateListOf<String>()

    var enabledNextButton by mutableStateOf(true)

    fun togglePlayer(
        playerOptions: NightTargetOptionsUiModel, target: PlayerWithRoleUiModel
    ) {
        val player = playerOptions.player
        if (disableActionKeysSelections.contains(playerOptions.key)) return
        _selectedPlayers.update {
            it.toMutableMap().apply {
                val playerTargets = get(player)
                if (playerTargets?.contains(target) == true) {
                    put(
                        player,
                        playerTargets.filter { p -> p.player.id != target.player.id }
                            .toImmutableList()
                    )
                } else {
                    put(player, ((playerTargets ?: emptyList()) + target).toImmutableList())
                }
            }
        }
    }

    fun applyActions() = viewModelScope.launch(coroutineDispatcher) {
        handleNightActionsUseCase.invoke(
            _selectedPlayers.value.map { (player, target) ->
                val domainPlayer = player.toDomain()
                NightActionDescriptor(
                    player = domainPlayer,
                    targets = target.toDomains(),
                    replacementRole = if (domainPlayer.role.key != player.role.key) {
                        getSingleRoleDescriptorUseCase(player.role.key)
                    } else null
                )
            })
    }

    private suspend fun PlayerWithRoleUiModel.toDomain(): PlayerWithRole {
        if (domainPlayers == null) domainPlayers = getAllInRoomPlayersUseCase().first()
        return domainPlayers!!.first { it.player.id == player.id }
    }

    private suspend fun List<PlayerWithRoleUiModel>.toDomains() = map { it.toDomain() }


    suspend fun handleInstantAction(
        action: InstantAction,
        playerOptions: NightTargetOptionsUiModel,
        selectedPlayerRoles: List<PlayerWithRoleUiModel>?,
        onShowSnakeBar: suspend (StringResource, List<Any>) -> Unit,
    ) {
        when (action) {
            InstantAction.SHOW_ALIGNMENT -> {
                val selected = selectedPlayerRoles?.firstOrNull() ?: return
                val message = if (isRegularMafia(selected.role)) {
                    Resources.strings.correctGuess
                } else {
                    Resources.strings.wrongGuess
                }
                onShowSnakeBar(message, emptyList())
            }

            InstantAction.SHOW_ALIGNMENTS_COUNT -> {
                selectedPlayerRoles ?: return
                val (mafiaCount, nonMafiaCount) = countMafiaAndNonMafia(selectedPlayerRoles)
                onShowSnakeBar(Resources.strings.alignmentCount, listOf(mafiaCount, nonMafiaCount))
            }

            InstantAction.REVEAL_IF_CIVILIAN_FOR_MAFIA -> {
                val selected = selectedPlayerRoles?.firstOrNull() ?: return
                if (isRegularCivilian(selected.role)) {
                    onShowSnakeBar(
                        Resources.strings.purchaseMafiaSuccess,
                        emptyList()
                    )
                } else {
                    onShowSnakeBar(
                        Resources.strings.purchaseMafiaFailed,
                        emptyList()
                    )
                }
            }
        }
        disableActionKeysSelections.add(playerOptions.key)
    }


    private fun isRegularMafia(role: RoleUiModel): Boolean {
        return role.alignment == Alignment.Mafia && role.key != RoleKeys.GOD_FATHER
    }


    private fun isRegularCivilian(role: RoleUiModel): Boolean {
        return Regex(RegexUtils.CIVILIAN_REGEX).matches(role.key)
    }

    private fun countMafiaAndNonMafia(players: List<PlayerWithRoleUiModel>): Pair<Int, Int> {
        val mafiaCount = players.count { isRegularMafia(it.role) }
        val nonMafiaCount = players.size - mafiaCount
        return mafiaCount to nonMafiaCount
    }
}