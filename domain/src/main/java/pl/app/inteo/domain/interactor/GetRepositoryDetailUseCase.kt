package pl.app.inteo.domain.interactor

import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.repository.RepositoriesRepository

class GetRepositoryDetailUseCase constructor(
    private val repositoriesRepository: RepositoriesRepository
) {

    suspend operator fun invoke(params: RepositoriesType, repositoryName: String) =
        repositoriesRepository.getRepository(params, repositoryName)
}
