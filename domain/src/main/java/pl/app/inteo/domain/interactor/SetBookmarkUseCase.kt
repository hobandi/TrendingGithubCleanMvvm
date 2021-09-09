package pl.app.inteo.domain.interactor

import pl.app.inteo.domain.repository.RepositoriesRepository

class SetBookmarkUseCase constructor(
    private val repositoriesRepository: RepositoriesRepository
) {

    suspend operator fun invoke(repositoryName: String, bookmark: Boolean) =
        repositoriesRepository.setRepositoryBookmarked(repositoryName, bookmark)
}
