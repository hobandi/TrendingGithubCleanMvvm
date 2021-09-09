package pl.app.inteo.cache

import pl.app.inteo.cache.dao.BookmarkedDao
import pl.app.inteo.cache.dao.RepositoryDao
import pl.app.inteo.cache.mapper.toCacheEntity
import pl.app.inteo.cache.mapper.toEntity
import pl.app.inteo.cache.models.BookmarkedEntity
import pl.app.inteo.cache.utils.PreferencesHelper
import pl.app.inteo.data.models.RepositoriesTypeEntity
import pl.app.inteo.data.models.RepositoryEntity
import pl.app.inteo.data.repository.RepositoriesCache

class RepositoriesCacheImp constructor(
    private val repositoryDao: RepositoryDao,
    private val bookmarkedDao: BookmarkedDao,
    private val preferencesHelper: PreferencesHelper
) : RepositoriesCache {
    override suspend fun getRepositories() = repositoryDao.getRepositories().map { it.toEntity() }

    override suspend fun saveRepositories(
        repositoriesTypeEntity: RepositoriesTypeEntity,
        list: List<RepositoryEntity>
    ) = repositoryDao.insertAll(list.map { it.toCacheEntity(repositoriesTypeEntity) }.toList())

    override suspend fun getBookMarkedRepositories(repositoriesTypeEntity: RepositoriesTypeEntity): List<RepositoryEntity> {
        val bookmarked = repositoryDao.getUsersAndBookmarks()
            .filter { it.repository != null && it.bookmarked != null && it.bookmarked.isBookmarked && it.repository.repositoriesType == repositoriesTypeEntity.ordinal }
            .map { it.repository!!.toEntity() }
        return bookmarked
    }


    override suspend fun isBookmarked(repositoryName: String): Boolean {
        return bookmarkedDao.getBookMark(repositoryName)?.isBookmarked ?: false
    }

    override suspend fun setRepositoryBookmarked(repositoryName: String, bookmarked: Boolean): Int {
        return bookmarkedDao.getBookMark(repositoryName)?.let {
            bookmarkedDao.update(repositoryName, bookmarked)
        } ?: let {
            bookmarkedDao.insert(BookmarkedEntity(repositoryName, bookmarked))
            1
        }
    }

    override suspend fun clearRepositories() = repositoryDao.clear()

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override fun isCached() = repositoryDao.getRepositories().isNotEmpty()

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}
