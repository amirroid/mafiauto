package ir.amirroid.mafiauto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.usecase.settings.GetSettingsConfigurationUseCase
import ir.amirroid.mafiauto.ui_models.settings.toUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    getSettingsConfigurationUseCase: GetSettingsConfigurationUseCase
) : ViewModel() {
    val settingsConfiguration = getSettingsConfigurationUseCase()
        .map { it?.toUiModel() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}