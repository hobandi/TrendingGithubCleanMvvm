package pl.app.inteo.data.source

import pl.app.inteo.data.repository.RepositoryDataSource

open class RepositoryDataSourceFactory constructor(
    private val cacheDataStore: RepositoryDataSource,
    private val remoteDataStore: RepositoryDataSource
) {

    open suspend fun getDataSource() = if (cacheDataStore.isCached() && !cacheDataStore.isExpired()) {
        getCacheDataSource()
    } else {
        getRemoteDataSource()
    }

    private fun getRemoteDataSource() = remoteDataStore

    fun getCacheDataSource() = cacheDataStore
}
