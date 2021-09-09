package pl.app.inteo.data.repository

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity

interface RepositoryDataSource {
    // Remote and cache
    suspend fun getRepositories(repositoryType: RepositoriesTypeEntity): List<RepositoryEntity>

    // Cache
    suspend fun saveRepositories(repositoriesTypeEntity: RepositoriesTypeEntity, list: List<RepositoryEntity>)
    suspend fun getBookMarkedRepositories(repositoriesTypeEntity: RepositoriesTypeEntity): List<RepositoryEntity>
    suspend fun isBookmarked(repositoryName: String): Boolean
    suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean): Int
    suspend fun clearRepositories(): Int

    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
    fun isCached(): Boolean
}
