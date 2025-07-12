package ir.amirroid.mafiauto.domain.usecase.settings

import ir.amirroid.mafiauto.domain.model.settings.Settings
import ir.amirroid.mafiauto.domain.repository.SettingsRepository

class SetSettingsConfigurationUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(newConfig: Settings) = settingsRepository.setConfig(newConfig)
}