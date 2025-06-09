package ir.amirroid.mafiauto.intro.viewmodel

import androidx.lifecycle.ViewModel
import ir.amirroid.mafiauto.domain.model.RoleDescriptor
import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LobbyViewModel(
    getAllRolesUseCase: GetAllRoleDescriptorsUseCase
) : ViewModel() {
    val roles = getAllRolesUseCase()

    private val _selectedRoles = MutableStateFlow(emptySet<RoleDescriptor>())
    val selectedRoles = _selectedRoles.asStateFlow()

    fun toggle(roleDescriptor: RoleDescriptor) {
        _selectedRoles.update {
            if (it.contains(roleDescriptor)) it - roleDescriptor else it + roleDescriptor
        }
    }
}