package ir.amirroid.mafiauto.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.common.app.response.map
import ir.amirroid.mafiauto.domain.model.settings.Settings
import ir.amirroid.mafiauto.domain.usecase.settings.GetSettingsConfigurationUseCase
import ir.amirroid.mafiauto.domain.usecase.settings.SaveSelectedIconColorUseCase
import ir.amirroid.mafiauto.domain.usecase.settings.SetSettingsConfigurationUseCase
import ir.amirroid.mafiauto.domain.usecase.update.GetLatestUpdateInfoUseCase
import ir.amirroid.mafiauto.theme.theme.AppThemeUiModel
import ir.amirroid.mafiauto.ui_models.error.ErrorUiModel
import ir.amirroid.mafiauto.ui_models.error.mapErrors
import ir.amirroid.mafiauto.ui_models.settings.SettingsUiModel
import ir.amirroid.mafiauto.ui_models.settings.toDomain
import ir.amirroid.mafiauto.ui_models.settings.toUiModel
import ir.amirroid.mafiauto.ui_models.update.UpdateInfoUiModel
import ir.amirroid.mafiauto.ui_models.update.toUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    getSettingsConfigurationUseCase: GetSettingsConfigurationUseCase,
    private val setSettingsConfigurationUseCase: SetSettingsConfigurationUseCase,
    private val getLatestUpdateInfoUseCase: GetLatestUpdateInfoUseCase,
    private val saveSelectedIconColorUseCase: SaveSelectedIconColorUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _updateInfo =
        MutableStateFlow<Response<UpdateInfoUiModel, ErrorUiModel>>(Response.Idle)
    val updateInfo = _updateInfo.asStateFlow()

    val settingsConfiguration = getSettingsConfigurationUseCase()
        .map { it ?: Settings.defaultSettings }
        .map { it.toUiModel() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Settings.defaultSettings.toUiModel())


    fun updateConfigurations(newConfig: SettingsUiModel) = viewModelScope.launch(dispatcher) {
        if (newConfig.theme != settingsConfiguration.value.theme) {
            updateIconColor(newConfig.theme)
        }
        setSettingsConfigurationUseCase(newConfig.toDomain())
    }

    private fun updateIconColor(theme: AppThemeUiModel) {
        saveSelectedIconColorUseCase.invoke(theme.colorName)
    }

    fun fetchUpdateInfo() = viewModelScope.launch(dispatcher) {
        _updateInfo.update { Response.Loading }
        val newResponse = getLatestUpdateInfoUseCase()
            .map { it.toUiModel() }
            .mapErrors()
        _updateInfo.update { newResponse }
    }

    fun clearUpdateInfoResponse() = _updateInfo.update { Response.Idle }
}