package pl.app.inteo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.app.inteo.domain.models.SettingType
import pl.app.inteo.domain.models.Settings
import pl.app.inteo.domain.repository.SettingsRepository

class SettingsRepositoryImp constructor(
    private val appVersion: String,
) : SettingsRepository {
    override suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>> = flow {
        emit(getData(isNightMode))
    }

    // This should be came from api but we don't have api so we are crating locally
    private fun getData(isNightMode: Boolean): List<Settings> {
        val settingList = mutableListOf<Settings>()
        settingList.add(Settings(1, SettingType.SWITCH, "Theme mode", "", isNightMode))
        settingList.add(Settings(2, SettingType.EMPTY, "Clear cache", ""))
        settingList.add(Settings(2, SettingType.TEXT, "App version", appVersion))
        return settingList
    }
}
