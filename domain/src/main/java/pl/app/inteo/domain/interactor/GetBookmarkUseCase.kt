package pl.app.inteo.domain.interactor

import pl.app.inteo.domain.repository.RepositoriesRepository

class GetBookmarkUseCase constructor(
    private val repositoriesRepository: RepositoriesRepository
) {

    suspend operator fun invoke(repositoryName: String) =
        repositoriesRepository.isBookmarked(repositoryName)
}
