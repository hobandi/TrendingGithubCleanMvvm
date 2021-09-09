package pl.app.inteo.data.source

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity
import pl.app.inteo.data.repository.RepositoriesRemote
import pl.app.inteo.data.repository.RepositoryDataSource

class RepositoryRemoteDataSource constructor(
    private val repositoryRemote: RepositoriesRemote
) : RepositoryDataSource {

    override suspend fun getRepositories(repositoryType: RepositoriesTypeEntity): List<RepositoryEntity> =
        repositoryRemote.getRepository(repositoryType)

    override suspend fun saveRepositories(
        repositoriesTypeEntity: RepositoriesTypeEntity,
        list: List<RepositoryEntity>
    ) {
        throw UnsupportedOperationException()
    }

    override suspend fun getBookMarkedRepositories(repositoriesTypeEntity: RepositoriesTypeEntity): List<RepositoryEntity> {
        throw UnsupportedOperationException()
    }

    override suspend fun isBookmarked(repositoryName: String): Boolean {
        throw UnsupportedOperationException()
    }

    override suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean): Int {
        throw UnsupportedOperationException()
    }

    override suspend fun clearRepositories(): Int {
        throw UnsupportedOperationException()
    }

    override fun setLastCacheTime(lastCache: Long) {
        throw UnsupportedOperationException()
    }

    override fun isExpired(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isCached(): Boolean {
        throw UnsupportedOperationException()
    }

}
