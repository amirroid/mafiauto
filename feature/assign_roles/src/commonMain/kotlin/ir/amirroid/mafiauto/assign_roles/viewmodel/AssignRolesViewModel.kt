package ir.amirroid.mafiauto.assign_roles.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class AssignRolesViewModel(
    getAllRoleDescriptorsUseCase: GetAllRoleDescriptorsUseCase,
) : ViewModel() {
    val roles = getAllRoleDescriptorsUseCase()

    private val _selectedRoles = MutableStateFlow(emptySet<RoleDescriptor>())
    val selectedRoles = _selectedRoles.map { it.toList() }

    fun toggleRole(roleDescriptor: RoleDescriptor) {
        _selectedRoles.update {
            if (it.contains(roleDescriptor)) it - roleDescriptor else it + roleDescriptor
        }
    }
}