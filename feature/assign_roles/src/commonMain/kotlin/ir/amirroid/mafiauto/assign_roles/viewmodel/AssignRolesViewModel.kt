package ir.amirroid.mafiauto.assign_roles.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.usecase.player.GetSelectedPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import ir.amirroid.mafiauto.domain.usecase.role.SelectNewRolesUseCase
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import ir.amirroid.mafiauto.ui_models.role.toDomain
import ir.amirroid.mafiauto.ui_models.role.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class AssignRolesViewModel(
    getAllRoleDescriptorsUseCase: GetAllRoleDescriptorsUseCase,
    getAllSelectedPlayersUseCase: GetSelectedPlayersUseCase,
    private val selectNewRolesUseCase: SelectNewRolesUseCase
) : ViewModel() {
    val roles = getAllRoleDescriptorsUseCase().map { it.toUiModel() }

    val selectedPlayersCount = getAllSelectedPlayersUseCase.invoke().size

    private val _selectedRoles = MutableStateFlow(emptySet<RoleUiModel>())
    val selectedRoles = _selectedRoles.map { it.toList() }

    fun toggleRole(role: RoleUiModel) {
        _selectedRoles.update {
            if (it.contains(role)) it - role else it + role
        }
    }

    fun selectRoles() {
        selectNewRolesUseCase.invoke(_selectedRoles.value.map { it.toDomain() })
    }
}