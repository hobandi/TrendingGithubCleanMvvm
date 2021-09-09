package pl.app.inteo.data.repository

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity

interface RepositoriesRemote {
    suspend fun getRepository(repositoryType: RepositoriesTypeEntity): List<RepositoryEntity>
}
