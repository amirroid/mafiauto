package ir.amirroid.mafiauto.data.memory

import ir.amirroid.mafiauto.data.memory.base.MemoryHolder
import ir.amirroid.mafiauto.domain.model.RoleDescriptor

class RoleMemoryHolder : MemoryHolder<List<RoleDescriptor>> {
    private val _selectedRoles = mutableListOf<RoleDescriptor>()
    override val value: List<RoleDescriptor>
        get() = _selectedRoles

    override fun setValue(newValue: List<RoleDescriptor>) {
        _selectedRoles.clear()
        _selectedRoles.addAll(newValue)
    }
}