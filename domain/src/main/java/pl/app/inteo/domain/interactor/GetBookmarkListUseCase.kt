package pl.app.inteo.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.repository.RepositoriesRepository

class GetBookmarkListUseCase constructor(
    private val repository: RepositoriesRepository
) : BaseUseCase<GetBookmarkListUseCase.GetBookmarkListUseCaseData, Flow<List<Repository>>> {

    override suspend fun invoke(data: GetBookmarkListUseCaseData): Flow<List<Repository>> {
        return if (data.forceUpdate) {
            repository.requestForceUpdate()
            repository.getRepositories(data.params)
                .flatMapLatest { repository.getBookMarkedRepositories(data.params) }
        } else {
            repository.getBookMarkedRepositories(data.params)
        }
    }

    data class GetBookmarkListUseCaseData(val params: RepositoriesType, val forceUpdate: Boolean)
}
