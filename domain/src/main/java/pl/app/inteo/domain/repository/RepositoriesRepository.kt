package pl.app.inteo.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository

interface RepositoriesRepository {
    // Remote and cache
    suspend fun getRepositories(repositoryType: RepositoriesType): Flow<List<Repository>>
    suspend fun getRepository(repositoryType: RepositoriesType, repositoryName: String): Flow<Repository>

    // Cache
    suspend fun saveRepositories(repositoryType: RepositoriesType, list: List<Repository>)
    suspend fun getBookMarkedRepositories(repositoryType: RepositoriesType): Flow<List<Repository>>
    suspend fun isBookmarked(repositoryName: String): Flow<Boolean>
    suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean): Flow<Int>
    fun requestForceUpdate()
}
