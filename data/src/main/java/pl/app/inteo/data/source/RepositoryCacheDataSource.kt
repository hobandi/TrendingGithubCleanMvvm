package pl.app.inteo.data.source

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity
import pl.app.inteo.data.repository.RepositoriesCache
import pl.app.inteo.data.repository.RepositoryDataSource

class RepositoryCacheDataSource constructor(
    private val repositoriesCache: RepositoriesCache
) : RepositoryDataSource {

    override suspend fun getRepositories(repositoryType: RepositoriesTypeEntity) = repositoriesCache.getRepositories()
    override suspend fun saveRepositories(
        repositoriesTypeEntity: RepositoriesTypeEntity,
        list: List<RepositoryEntity>
    ) {
        repositoriesCache.saveRepositories(repositoriesTypeEntity, list)
        setLastCacheTime(System.currentTimeMillis())
    }

    override suspend fun getBookMarkedRepositories(repositoriesTypeEntity: RepositoriesTypeEntity): List<RepositoryEntity> =
        repositoriesCache.getBookMarkedRepositories(repositoriesTypeEntity)

    override suspend fun isBookmarked(repositoryName: String): Boolean = repositoriesCache.isBookmarked(repositoryName)

    override suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean) =
        repositoriesCache.setRepositoryBookmarked(repositoryName, bookmarked)

    override suspend fun clearRepositories() = repositoriesCache.clearRepositories()

    override fun setLastCacheTime(lastCache: Long) = repositoriesCache.setLastCacheTime(lastCache)

    override fun isExpired() = repositoriesCache.isExpired()

    override fun isCached() = repositoriesCache.isCached()
}
