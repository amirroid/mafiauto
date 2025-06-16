package ir.amirroid.mafiauto.assign_roles.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.model.Alignment
import ir.amirroid.mafiauto.domain.usecase.player.GetSelectedPlayersUseCase
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import ir.amirroid.mafiauto.domain.usecase.role.SelectNewRolesUseCase
import ir.amirroid.mafiauto.ui_models.role.RoleUiModel
import ir.amirroid.mafiauto.ui_models.role.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class AssignRolesViewModel(
    getAllRoleDescriptorsUseCase: GetAllRoleDescriptorsUseCase,
    getAllSelectedPlayersUseCase: GetSelectedPlayersUseCase,
    private val selectNewRolesUseCase: SelectNewRolesUseCase
) : ViewModel() {
    private val rolesDescriptors = getAllRoleDescriptorsUseCase()
    val roles = rolesDescriptors.map { it.toUiModel() }

    private val _selectedRoles = MutableStateFlow(emptySet<RoleUiModel>())
    val selectedRoles = _selectedRoles.map { it.toList() }

    var selectedRoleToPreviewExplanation by mutableStateOf<RoleUiModel?>(null)
    private val selectedPlayersCount = getAllSelectedPlayersUseCase.invoke().size

    fun toggleRole(role: RoleUiModel) {
        _selectedRoles.update {
            if (it.contains(role)) it - role else it + role
        }
    }

    fun showPreview(role: RoleUiModel) {
        selectedRoleToPreviewExplanation = role
    }

    fun dismissPreview() {
        selectedRoleToPreviewExplanation = null
    }

    fun selectRoles() {
        selectNewRolesUseCase.invoke(_selectedRoles.value.map { it.toDomain() })
    }

    fun checkConditions(): Boolean {
        val selectedRole = _selectedRoles.value
        return selectedRole.size == selectedPlayersCount &&
                selectedRole.count { it.toDomain().alignment != Alignment.Mafia } > selectedRole.count { it.toDomain().alignment == Alignment.Mafia } &&
                selectedRole.count { it.toDomain().alignment == Alignment.Mafia } > 0
    }

    private fun RoleUiModel.toDomain() = rolesDescriptors.first { it.key == key }
}