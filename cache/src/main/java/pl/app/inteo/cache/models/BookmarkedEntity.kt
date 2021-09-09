package pl.app.inteo.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.app.inteo.cache.CacheConstants.BOOKMARKED_TABLE_NAME

@Entity(tableName = BOOKMARKED_TABLE_NAME)
data class BookmarkedEntity(
    @PrimaryKey
    val parentRepositoryName: String,
    val isBookmarked: Boolean
)