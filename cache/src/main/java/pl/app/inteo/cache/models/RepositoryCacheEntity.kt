package pl.app.inteo.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pl.app.inteo.cache.CacheConstants
import pl.app.inteo.cache.converters.DbConverters

@Entity(tableName = CacheConstants.REPOSITORIES_TABLE_NAME)
@TypeConverters(DbConverters::class)
data class RepositoryCacheEntity(
    @PrimaryKey
    val repositoryName: String,
    val forks: Int,
    val starsSince: Int,
    val builtBy: List<BuiltByCacheEntity>,
    val totalStars: Int,
    val rank: Int,
    val description: String?,
    val language: String?,
    val languageColor: String?,
    val url: String?,
    val username: String?,
    val since: String?,
    val repositoriesType: Int
)
