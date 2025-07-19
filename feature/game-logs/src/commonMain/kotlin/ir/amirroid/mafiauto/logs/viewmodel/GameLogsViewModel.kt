package ir.amirroid.mafiauto.logs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.game.GetAllGameLogsUseCase
import ir.amirroid.mafiauto.ui_models.log.toUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class GameLogsViewModel(
    getAllGameLogsUseCase: GetAllGameLogsUseCase
) : ViewModel() {
    val logs = getAllGameLogsUseCase()
        .map { logs -> logs.map { it.toUiModel() }.toImmutableList() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, persistentListOf())
}