package ir.amirroid.mafiauto.data.memory

import ir.amirroid.mafiauto.data.memory.base.MemoryHolder
import ir.amirroid.mafiauto.domain.model.player.Player

class PlayerMemoryHolder : MemoryHolder<List<Player>> {
    private val _selectedPlayers = mutableListOf<Player>()
    override val value: List<Player>
        get() = _selectedPlayers

    override fun setValue(newValue: List<Player>) {
        _selectedPlayers.clear()
        _selectedPlayers.addAll(newValue)
    }
}