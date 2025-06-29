package ir.amirroid.mafiauto.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.domain.model.Settings
import ir.amirroid.mafiauto.domain.usecase.settings.GetSettingsConfigurationUseCase
import ir.amirroid.mafiauto.domain.usecase.settings.SetSettingsConfigurationUseCase
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import ir.amirroid.mafiauto.ui_models.settings.toDomain
import ir.amirroid.mafiauto.ui_models.settings.toUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    getSettingsConfigurationUseCase: GetSettingsConfigurationUseCase,
    private val setSettingsConfigurationUseCase: SetSettingsConfigurationUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val settingsConfiguration = getSettingsConfigurationUseCase()
        .map { it ?: Settings.defaultSettings }
        .map { it.toUiModel() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Settings.defaultSettings.toUiModel())


    fun updateConfigurations(newConfig: SettingsUiModel) = viewModelScope.launch(dispatcher) {
        setSettingsConfigurationUseCase(newConfig.toDomain())
    }
}