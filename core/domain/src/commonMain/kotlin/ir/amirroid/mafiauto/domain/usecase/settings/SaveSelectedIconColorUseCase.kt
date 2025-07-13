package ir.amirroid.mafiauto.domain.usecase.settings

import ir.amirroid.mafiauto.domain.repository.SettingsRepository

class SaveSelectedIconColorUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(colorName: String) = settingsRepository.saveSelectedIconColor(colorName)
}