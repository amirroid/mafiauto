package ir.amirroid.mafiauto.domain.usecase.settings

import ir.amirroid.mafiauto.domain.repository.SettingsRepository

class GetSettingsConfigurationUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getConfig()
}