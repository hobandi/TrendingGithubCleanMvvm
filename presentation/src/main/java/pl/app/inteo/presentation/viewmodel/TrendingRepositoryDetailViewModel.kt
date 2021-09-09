package pl.app.inteo.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import pl.app.inteo.domain.interactor.GetBookmarkUseCase
import pl.app.inteo.domain.interactor.GetRepositoryDetailUseCase
import pl.app.inteo.domain.interactor.SetBookmarkUseCase
import pl.app.inteo.presentation.mappers.toDomain
import pl.app.inteo.presentation.mappers.toPresentationUiModel
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.model.RepositoryUiModel
import pl.app.inteo.presentation.utils.CoroutineContextProvider
import pl.app.inteo.presentation.utils.ExceptionHandler

sealed class TrendingRepositoryDetailViewModelUIModel {
    object Loading : TrendingRepositoryDetailViewModelUIModel()
    data class Error(@StringRes val messageRes: Int) : TrendingRepositoryDetailViewModelUIModel()
    data class Success(val data: RepositoryUiModel) : TrendingRepositoryDetailViewModelUIModel()
}

sealed class BookmarkDetailViewModelUIModel {
    data class SuccessGetBookmark(val bookmark: Boolean) : BookmarkDetailViewModelUIModel()
    data class SuccessSetBookmark(val bookmark: Boolean) : BookmarkDetailViewModelUIModel()
}

class TrendingRepositoryDetailViewModel constructor(
    private val repositoryName: String,
    private val repositoryType: RepositoriesTypeUiModel,
    contextProvider: CoroutineContextProvider,
    private val getRepositoryDetailUseCase: GetRepositoryDetailUseCase,
    private val getBookmarkUseCase: GetBookmarkUseCase,
    private val setBookmarkUseCase: SetBookmarkUseCase
) : BaseViewModel(contextProvider) {

    private var _repository: MutableLiveData<TrendingRepositoryDetailViewModelUIModel> =
        MutableLiveData(TrendingRepositoryDetailViewModelUIModel.Loading)

    private var _bookmark: MutableLiveData<BookmarkDetailViewModelUIModel> = MutableLiveData()

    val repository: LiveData<TrendingRepositoryDetailViewModelUIModel> get() = _repository

    val bookmark: LiveData<BookmarkDetailViewModelUIModel> get() = _bookmark

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _repository.postValue(
            TrendingRepositoryDetailViewModelUIModel.Error(
                ExceptionHandler.parseToStringResMessage(
                    exception
                )
            )
        )
    }

    fun loadRepositoryAndBookmark() = launchCoroutineIO {
        getRepositoryDetailUseCase(repositoryType.toDomain(), repositoryName).collect {
            _repository.postValue(TrendingRepositoryDetailViewModelUIModel.Success(it.toPresentationUiModel()))
        }
        getBookmarkUseCase(repositoryName).collect {
            _bookmark.postValue(BookmarkDetailViewModelUIModel.SuccessGetBookmark(it))
        }
    }

    fun setBookmark(bookmark: Boolean) = launchCoroutineIO {
        setBookmarkUseCase(repositoryName, bookmark).collect {
            _bookmark.postValue(BookmarkDetailViewModelUIModel.SuccessSetBookmark(bookmark))
        }
    }
}
