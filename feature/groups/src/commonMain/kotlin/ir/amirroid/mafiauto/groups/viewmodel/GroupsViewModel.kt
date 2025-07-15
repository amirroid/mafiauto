package ir.amirroid.mafiauto.groups.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.common.app.utils.emptyImmutableList
import ir.amirroid.mafiauto.domain.usecase.groups.AddNewGroupUseCase
import ir.amirroid.mafiauto.domain.usecase.groups.DeleteGroupUseCase
import ir.amirroid.mafiauto.domain.usecase.groups.GetAllGroupsUseCase
import ir.amirroid.mafiauto.ui_models.group_with_player.GroupWithPlayersUiModel
import ir.amirroid.mafiauto.ui_models.group_with_player.toUiModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GroupsViewModel(
    getAllGroupsUseCase: GetAllGroupsUseCase,
    private val addNewGroupUseCase: AddNewGroupUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val groups = getAllGroupsUseCase()
        .map { groupWithPlayers -> groupWithPlayers.map { it.toUiModel() }.toImmutableList() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            emptyImmutableList()
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

    fun deleteGroup(group: GroupWithPlayersUiModel) = viewModelScope.launch(dispatcher) {
        deleteGroupUseCase.invoke(group.groupId)
    }
}