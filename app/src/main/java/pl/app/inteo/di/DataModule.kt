package pl.app.inteo.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import pl.app.inteo.BuildConfig
import pl.app.inteo.data.RepositoriesRepositoryImp
import pl.app.inteo.data.SettingsRepositoryImp
import pl.app.inteo.data.repository.RepositoryDataSource
import pl.app.inteo.data.source.RepositoryCacheDataSource
import pl.app.inteo.data.source.RepositoryDataSourceFactory
import pl.app.inteo.data.source.RepositoryRemoteDataSource
import pl.app.inteo.domain.repository.RepositoriesRepository
import pl.app.inteo.domain.repository.SettingsRepository

val dataModule = module {
    factory<RepositoryDataSource>(named("local")) { RepositoryCacheDataSource(get()) }
    factory<RepositoryDataSource>(named("remote")) { RepositoryRemoteDataSource(get()) }
    factory { RepositoryDataSourceFactory(get(named("local")), get(named("remote"))) }
    single<RepositoriesRepository> { RepositoriesRepositoryImp(get()) }
    single<SettingsRepository> { SettingsRepositoryImp(BuildConfig.VERSION_NAME) }
}
