package ir.amirroid.mafiauto.data.memory.base

interface MemoryHolder<T> {
    val value: T

    fun setValue(newValue: T)
}