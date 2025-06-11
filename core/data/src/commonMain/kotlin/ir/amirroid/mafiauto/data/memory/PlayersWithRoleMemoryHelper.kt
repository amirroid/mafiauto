package ir.amirroid.mafiauto.data.memory

import ir.amirroid.mafiauto.data.memory.base.MemoryHolder
import ir.amirroid.mafiauto.domain.model.PlayerWithRole

class PlayersWithRoleMemoryHelper : MemoryHolder<List<PlayerWithRole>> {
    private val _value = mutableListOf<PlayerWithRole>()
    override val value: List<PlayerWithRole>
        get() = _value

    override fun setValue(newValue: List<PlayerWithRole>) {
        _value.clear()
        _value.addAll(newValue)
    }
}