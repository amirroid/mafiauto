package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setConfig(newConfig: Settings)
    fun getConfig(): Flow<Settings?>
}