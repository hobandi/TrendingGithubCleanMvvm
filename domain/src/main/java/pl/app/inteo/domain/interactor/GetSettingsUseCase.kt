package pl.app.inteo.domain.interactor

import kotlinx.coroutines.flow.Flow
import pl.app.inteo.domain.models.Settings
import pl.app.inteo.domain.repository.SettingsRepository

typealias GetSettingsBaseUseCase = BaseUseCase<Boolean, Flow<List<Settings>>>

class GetSettingsUseCase constructor(
    private val settingsRepository: SettingsRepository
) : GetSettingsBaseUseCase {

    override suspend operator fun invoke(params: Boolean) = settingsRepository.getSettings(params)
}
