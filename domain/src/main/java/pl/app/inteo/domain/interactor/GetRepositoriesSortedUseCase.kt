package pl.app.inteo.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.models.SortingType
import pl.app.inteo.domain.repository.RepositoriesRepository
import pl.app.inteo.domain.sorting.sort

class GetRepositoriesSortedUseCase constructor(
    private val repositories: RepositoriesRepository
) : BaseUseCase<GetRepositoriesSortedUseCase.GetRepositoriesSortedUseCaseData, Flow<List<Repository>>> {

    override suspend operator fun invoke(data: GetRepositoriesSortedUseCaseData): Flow<List<Repository>> =
        repositories.also { if (data.forceUpdate) it.requestForceUpdate() }.getRepositories(data.params).flatMapLatest {
            flow { emit(it.sort(data.sortingType)) }
        }

    data class GetRepositoriesSortedUseCaseData(
        val params: RepositoriesType,
        val forceUpdate: Boolean,
        val sortingType: SortingType,
    )
}
