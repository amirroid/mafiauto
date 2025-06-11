package ir.amirroid.mafiauto.domain.repository

import kotlinx.coroutines.flow.StateFlow


interface GameRepository {
    val statusChecksCount: StateFlow<Int>
    fun onStatusChecked()
    fun undoStatusCheck()
}