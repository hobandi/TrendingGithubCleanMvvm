package pl.app.inteo.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import pl.app.inteo.cache.CacheConstants
import pl.app.inteo.cache.Migrations
import pl.app.inteo.cache.converters.DbConverters
import pl.app.inteo.cache.dao.BookmarkedDao
import pl.app.inteo.cache.dao.RepositoryDao
import pl.app.inteo.cache.models.BookmarkedEntity
import pl.app.inteo.cache.models.RepositoryCacheEntity

@Database(
    entities = [RepositoryCacheEntity::class, BookmarkedEntity::class],
    version = Migrations.DB_VERSION,
    exportSchema = false
)
@TypeConverters(DbConverters::class)
abstract class CacheDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao

    abstract fun bookmarkedDao(): BookmarkedDao

    companion object {

        private var INSTANCE: CacheDatabase? = null

        lateinit var moshi: Moshi

        fun getInstance(context: Context): CacheDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CacheDatabase::class.java,
            CacheConstants.DB_NAME
        ).build()
    }
}
