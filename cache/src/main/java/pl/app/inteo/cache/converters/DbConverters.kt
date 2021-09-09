package pl.app.inteo.cache.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Types
import pl.app.inteo.cache.database.CacheDatabase
import pl.app.inteo.cache.models.BuiltByCacheEntity

class DbConverters {

    @TypeConverter
    fun builtByCacheEntityToString(builtByCacheEntity: List<BuiltByCacheEntity>): String? {
        val type = Types.newParameterizedType(List::class.java, BuiltByCacheEntity::class.java)
        return CacheDatabase.moshi.adapter<List<BuiltByCacheEntity>>(type).toJson(builtByCacheEntity)
    }

    @TypeConverter
    fun stringToBuiltByCache(builtByCacheEntity: String): List<BuiltByCacheEntity>? {
        val type = Types.newParameterizedType(List::class.java, BuiltByCacheEntity::class.java)
        return CacheDatabase.moshi.adapter<List<BuiltByCacheEntity>>(type).fromJson(builtByCacheEntity)
    }
}
