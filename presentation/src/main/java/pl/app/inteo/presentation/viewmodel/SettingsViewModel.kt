package pl.app.inteo.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import pl.app.inteo.domain.interactor.GetSettingsUseCase
import pl.app.inteo.domain.models.Settings
import pl.app.inteo.presentation.utils.CoroutineContextProvider
import pl.app.inteo.presentation.utils.ExceptionHandler
import pl.app.inteo.presentation.utils.PresentationPreferencesHelper

sealed class SettingUIModel {
    data class Error(@StringRes val messageRes: Int) : SettingUIModel()
    data class Success(val data: List<Settings>) : SettingUIModel()
    data class NightMode(val nightMode: Boolean) : SettingUIModel()
}

class SettingsViewModel constructor(
    contextProvider: CoroutineContextProvider,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val preferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _settings = MutableLiveData<SettingUIModel>()
    val settings: LiveData<SettingUIModel> = _settings

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _settings.postValue(
            SettingUIModel.Error(
                ExceptionHandler.parseToStringResMessage(
                    exception
                )
            )
        )
    }

    fun getSettings() = launchCoroutineIO {
        loadSettings()
    }

    private suspend fun loadSettings() = getSettingsUseCase(preferencesHelper.isNightMode).collect {
        _settings.postValue(SettingUIModel.Success(it))
    }

    fun setSettings(selectedSetting: Settings) {
        selectedSetting.run {
            preferencesHelper.isNightMode = selectedValue
            _settings.postValue(SettingUIModel.NightMode(selectedValue))
        }
    }
}
