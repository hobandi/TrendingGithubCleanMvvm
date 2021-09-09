package pl.app.inteo.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.app.inteo.domain.models.Settings

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>>
}
