package ir.amirroid.mafiauto.data.repository.settings

import ir.amirroid.mafiauot.preferences.source.SettingsConfigPreferencesSource
import ir.amirroid.mafiauto.data.mappers.settings.toData
import ir.amirroid.mafiauto.data.mappers.settings.toDomain
import ir.amirroid.mafiauto.domain.model.Settings
import ir.amirroid.mafiauto.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val settingsSource: SettingsConfigPreferencesSource
) : SettingsRepository {
    override suspend fun setConfig(newConfig: Settings) {
        return settingsSource.set(newConfig.toData())
    }

    override fun getConfig(): Flow<Settings?> {
        return settingsSource.get().map { it?.toDomain() }
    }
}