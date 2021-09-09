package pl.app.inteo.domain.interactor

import kotlinx.coroutines.flow.Flow
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.repository.RepositoriesRepository

class GetRepositoriesUseCase constructor(
    private val repositories: RepositoriesRepository
) : BaseUseCase<GetRepositoriesUseCase.GetRepositoriesUseCaseData, Flow<List<Repository>>> {

    override suspend operator fun invoke(data: GetRepositoriesUseCaseData): Flow<List<Repository>> =
        repositories.also { if (data.forceUpdate) it.requestForceUpdate() }.getRepositories(data.params)

    data class GetRepositoriesUseCaseData(
        val params: RepositoriesType,
        val forceUpdate: Boolean,
    )
}
