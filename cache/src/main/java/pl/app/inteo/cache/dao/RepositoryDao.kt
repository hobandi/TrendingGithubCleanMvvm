package pl.app.inteo.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import pl.app.inteo.cache.models.BookmarkedRepositoriesRelation
import pl.app.inteo.cache.models.RepositoryCacheEntity

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories")
    fun getRepositories(): List<RepositoryCacheEntity>

    @Query("SELECT * FROM repositories WHERE repositoryName = :repositoryName")
    fun getRepository(repositoryName: String): RepositoryCacheEntity

    @Transaction
    @Query("SELECT * FROM repositories")
    fun getUsersAndBookmarks(): List<BookmarkedRepositoriesRelation>

    @Query("DELETE FROM repositories")
    fun clear(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<RepositoryCacheEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: RepositoryCacheEntity)

}
