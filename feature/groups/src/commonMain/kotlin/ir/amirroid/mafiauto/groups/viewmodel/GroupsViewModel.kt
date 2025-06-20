package ir.amirroid.mafiauto.groups.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.groups.AddNewGroupUseCase
import ir.amirroid.mafiauto.domain.usecase.groups.GetAllGroupsUseCase
import ir.amirroid.mafiauto.ui_models.group.toUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupsViewModel(
    getAllGroupsUseCase: GetAllGroupsUseCase,
    private val addNewGroupUseCase: AddNewGroupUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val groups = getAllGroupsUseCase()
        .map { groupWithPlayers -> groupWithPlayers.map { it.toUiModel() } }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyList()
        )

    var visibleAddNewGroup by mutableStateOf(false)

    fun showAddNewGroup() {
        visibleAddNewGroup = true
    }

    fun hideAddNewGroup() {
        visibleAddNewGroup = false
    }

    fun addGroup(name: String) = viewModelScope.launch(dispatcher) {
        addNewGroupUseCase(name)
    }
}