package pl.app.inteo.presentation.viewmodel

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import pl.app.inteo.domain.interactor.GetBookmarkListUseCase
import pl.app.inteo.domain.interactor.GetBookmarkListUseCase.GetBookmarkListUseCaseData
import pl.app.inteo.domain.interactor.GetRepositoriesSortedUseCase
import pl.app.inteo.domain.interactor.GetRepositoriesSortedUseCase.GetRepositoriesSortedUseCaseData
import pl.app.inteo.domain.interactor.GetRepositoriesUseCase
import pl.app.inteo.presentation.mappers.toDomain
import pl.app.inteo.presentation.mappers.toPresentationUiModel
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.model.RepositoryUiModel
import pl.app.inteo.presentation.model.SortingTypeUiModel
import pl.app.inteo.presentation.utils.CoroutineContextProvider
import pl.app.inteo.presentation.utils.ExceptionHandler

sealed class TrendingRepositoriesUIModel {
    object Loading : TrendingRepositoriesUIModel()
    data class Error(@StringRes val messageRes: Int) : TrendingRepositoriesUIModel()
    data class Success(val data: List<RepositoryUiModel>) : TrendingRepositoriesUIModel()
}

class TrendingRepositoriesViewModel constructor(
    contextProvider: CoroutineContextProvider,
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
    private val getBookmarkListUseCase: GetBookmarkListUseCase,
    private val getRepositoriesSortedUseCase: GetRepositoriesSortedUseCase,
) : BaseViewModel(contextProvider) {

    private var _repositoryList: MutableLiveData<TrendingRepositoriesUIModel> =
        MutableLiveData(TrendingRepositoriesUIModel.Loading)

    var selectedRepositoriesType: RepositoriesTypeUiModel = RepositoriesTypeUiModel.DAILY

    var selectedSortedType: SortingTypeUiModel = SortingTypeUiModel.NO_SORTING

    val repositoryList: LiveData<TrendingRepositoriesUIModel> get() = _repositoryList

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("Exception", "CoroutineExceptionHandler", exception)
        _repositoryList.postValue(
            TrendingRepositoriesUIModel.Error(
                ExceptionHandler.parseToStringResMessage(
                    exception
                )
            )
        )
    }

    fun loadBookmarkedList(repositoryType: RepositoriesTypeUiModel, forceUpdate: Boolean = false) = launchCoroutineIO {
        getBookmarkListUseCase(
            GetBookmarkListUseCaseData(
                repositoryType.toDomain(),
                forceUpdate
            )
        ).collect {
            _repositoryList.postValue(TrendingRepositoriesUIModel.Success(it.toPresentationUiModel()))
        }
    }

    fun loadSortedRepository(sortingTypeUiModel: SortingTypeUiModel) =
        launchCoroutineIO {
            getRepositoriesSortedUseCase(
                GetRepositoriesSortedUseCaseData(
                    selectedRepositoriesType.toDomain(),
                    false,
                    sortingTypeUiModel.toDomain()
                )
            ).collect {
                Log.d("uiView", "$it")
                _repositoryList.postValue(TrendingRepositoriesUIModel.Success(it.toPresentationUiModel()))
            }
        }

    fun loadRepositoryList(repositoryType: RepositoriesTypeUiModel, forceUpdate: Boolean = false) = launchCoroutineIO {
        _repositoryList.postValue(TrendingRepositoriesUIModel.Loading)
        getRepositoriesSortedUseCase(
            GetRepositoriesSortedUseCaseData(
                repositoryType.toDomain(),
                forceUpdate,
                selectedSortedType.toDomain()
            )
        ).collect {
            _repositoryList.postValue(TrendingRepositoriesUIModel.Success(it.toPresentationUiModel()))
        }
    }
}
