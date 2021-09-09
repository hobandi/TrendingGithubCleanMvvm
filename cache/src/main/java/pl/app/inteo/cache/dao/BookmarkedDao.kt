package pl.app.inteo.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.app.inteo.cache.models.BookmarkedEntity

@Dao
interface BookmarkedDao {

    @Query("UPDATE bookmarked SET isBookMarked = :bookmarked WHERE parentRepositoryName = :repositoryName")
    fun update(repositoryName: String, bookmarked: Boolean): Int

    @Query("SELECT isBookmarked FROM bookmarked WHERE parentRepositoryName = :repositoryName")
    fun isUserBookmarked(repositoryName: String): Boolean

    @Query("SELECT * FROM bookmarked WHERE parentRepositoryName= :repositoryName")
    fun getBookMark(repositoryName: String): BookmarkedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarked: BookmarkedEntity)

    @Query("DELETE FROM bookmarked")
    fun clear(): Int
}
