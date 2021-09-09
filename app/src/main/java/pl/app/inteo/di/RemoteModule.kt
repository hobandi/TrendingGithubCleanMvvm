package pl.app.inteo.di

import org.koin.dsl.module
import pl.app.inteo.BuildConfig
import pl.app.inteo.data.repository.RepositoriesRemote
import pl.app.inteo.remote.api.ServiceFactory
import pl.app.inteo.remote.repository.RepositoriesRemoteImp

val remoteModule = module {
    single { ServiceFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL) }
    single<RepositoriesRemote> { RepositoriesRemoteImp(get()) }
}
