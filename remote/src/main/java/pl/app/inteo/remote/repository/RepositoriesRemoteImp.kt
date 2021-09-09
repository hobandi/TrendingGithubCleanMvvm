package pl.app.inteo.remote.repository

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.repository.RepositoriesRemote
import pl.app.inteo.remote.api.RepositoriesService
import pl.app.inteo.remote.mappers.toEntity
import pl.app.inteo.remote.mappers.toRemote

class RepositoriesRemoteImp constructor(
    private val repositoryService: RepositoriesService
) : RepositoriesRemote {

    override suspend fun getRepository(repositoryType: RepositoriesTypeEntity) =
        repositoryService.getRepositories(repositoryType.toRemote()).map {
            it.toEntity(repositoryType)
        }
}
