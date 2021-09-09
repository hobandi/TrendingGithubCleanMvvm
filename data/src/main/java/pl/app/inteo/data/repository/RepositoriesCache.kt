package pl.app.inteo.data.repository

import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity

interface RepositoriesCache {
    suspend fun getRepositories(): List<RepositoryEntity>
    suspend fun saveRepositories(repositoriesTypeEntity: RepositoriesTypeEntity, list: List<RepositoryEntity>)
    suspend fun getBookMarkedRepositories(repositoriesTypeEntity: RepositoriesTypeEntity): List<RepositoryEntity>
    suspend fun isBookmarked(repositoryName: String): Boolean
    suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean): Int
    suspend fun clearRepositories(): Int
    fun setLastCacheTime(lastCache: Long)
    fun isCached(): Boolean
    fun isExpired(): Boolean
}
