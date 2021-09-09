package pl.app.inteo.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.app.inteo.domain.interactor.GetBookmarkListUseCase
import pl.app.inteo.domain.interactor.GetBookmarkUseCase
import pl.app.inteo.domain.interactor.GetRepositoriesSortedUseCase
import pl.app.inteo.domain.interactor.GetRepositoriesUseCase
import pl.app.inteo.domain.interactor.GetRepositoryDetailUseCase
import pl.app.inteo.domain.interactor.GetSettingsUseCase
import pl.app.inteo.domain.interactor.SetBookmarkUseCase
import pl.app.inteo.presentation.interfaces.ChangeTheme
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.utils.CoroutineContextProvider
import pl.app.inteo.presentation.utils.CoroutineContextProviderImp
import pl.app.inteo.presentation.utils.PresentationPreferencesHelper
import pl.app.inteo.presentation.viewmodel.MainViewModel
import pl.app.inteo.presentation.viewmodel.SettingsViewModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoriesViewModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModel
import pl.app.inteo.settings.ChangeThemeImpl

val presentationModule = module {
    single<CoroutineContextProvider> { CoroutineContextProviderImp() }
    factory { GetRepositoriesUseCase(get()) }
    factory { GetRepositoryDetailUseCase(get()) }
    factory { GetBookmarkUseCase(get()) }
    factory { SetBookmarkUseCase(get()) }
    factory { GetBookmarkListUseCase(get()) }
    factory { GetRepositoriesSortedUseCase(get()) }
    factory { GetSettingsUseCase(get()) }
    single<ChangeTheme> { ChangeThemeImpl() }
    factory { PresentationPreferencesHelper(androidContext()) }
    viewModel { TrendingRepositoriesViewModel(get(), get(), get(), get()) }
    viewModel { MainViewModel() }
    viewModel { SettingsViewModel(get(), get(), get()) }
    viewModel { (repositoryName: String, repositoryType: RepositoriesTypeUiModel) ->
        TrendingRepositoryDetailViewModel(
            repositoryName,
            repositoryType,
            get(),
            get(),
            get(),
            get()
        )
    }
}
