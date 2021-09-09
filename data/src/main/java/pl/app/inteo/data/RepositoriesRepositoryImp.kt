package pl.app.inteo.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pl.app.inteo.data.mapper.toDomain
import pl.app.inteo.data.mapper.toEntity
import pl.app.inteo.data.source.RepositoryDataSourceFactory
import pl.app.inteo.data.source.RepositoryRemoteDataSource
import pl.app.inteo.domain.models.RepositoriesType
import pl.app.inteo.domain.models.Repository
import pl.app.inteo.domain.repository.RepositoriesRepository

class RepositoriesRepositoryImp constructor(
    private val dataSourceFactory: RepositoryDataSourceFactory,
) : RepositoriesRepository {

    override suspend fun getRepositories(repositoryType: RepositoriesType): Flow<List<Repository>> = flow {
        emit(getListAndSaveIfNeed(repositoryType))
    }

    override suspend fun getRepository(repositoryType: RepositoriesType, repositoryName: String): Flow<Repository> =
        flow {
            emit(getListAndSaveIfNeed(repositoryType).first { it.repositoryName == repositoryName })
        }

    private suspend fun getListAndSaveIfNeed(repositoryType: RepositoriesType): List<Repository> {
        val dataSource = dataSourceFactory.getDataSource()
        return dataSource.getRepositories(repositoryType.toEntity()).also {
            if (dataSource is RepositoryRemoteDataSource) {
                dataSourceFactory.getCacheDataSource().apply {
                    clearRepositories()
                }.saveRepositories(repositoryType.toEntity(), it)
            }
        }.map {
            it.toDomain()
        }
    }

    override suspend fun saveRepositories(repositoryType: RepositoriesType, list: List<Repository>) = list.map {
        it.toEntity()
    }.run {
        dataSourceFactory.getCacheDataSource().saveRepositories(repositoryType.toEntity(), this)
    }

    override suspend fun getBookMarkedRepositories(repositoryType: RepositoriesType): Flow<List<Repository>> = flow {
        emit(
            dataSourceFactory.getCacheDataSource().getBookMarkedRepositories(repositoryType.toEntity())
                .map { it.toDomain() })
    }

    override suspend fun isBookmarked(repositoryName: String): Flow<Boolean> = flow {
        emit(dataSourceFactory.getCacheDataSource().isBookmarked(repositoryName))
    }

    override suspend fun setRepositoryBookmarked(
        repositoryName: String,
        bookmarked: Boolean
    ): Flow<Int> = flow {
        emit(
            dataSourceFactory.getCacheDataSource()
                .setRepositoryBookmarked(repositoryName, bookmarked)
        )
    }

    override fun requestForceUpdate() {
        dataSourceFactory.getCacheDataSource().setLastCacheTime(0)
    }
}
