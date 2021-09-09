package pl.app.inteo.di

import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pl.app.inteo.cache.RepositoriesCacheImp
import pl.app.inteo.cache.database.CacheDatabase
import pl.app.inteo.cache.utils.PreferencesHelper
import pl.app.inteo.data.repository.RepositoriesCache

val cacheModule = module {
    single { Moshi.Builder().build() }
    factory {
        CacheDatabase.getInstance(androidContext()).apply {
            CacheDatabase.moshi = get()
        }
    }
    factory { get<CacheDatabase>().repositoryDao() }
    factory { get<CacheDatabase>().bookmarkedDao() }
    single { PreferencesHelper(androidContext()) }
    single<RepositoriesCache> { RepositoriesCacheImp(get(), get(), get()) }
}
