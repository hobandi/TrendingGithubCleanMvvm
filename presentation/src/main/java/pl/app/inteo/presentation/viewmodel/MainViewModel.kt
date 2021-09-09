package pl.app.inteo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel

sealed class MainViewModelUIModel {
    object HideBottomBar : MainViewModelUIModel()
    object ShowBottomBar : MainViewModelUIModel()
}

class MainViewModel : ViewModel() {

    private var _main: MutableLiveData<MainViewModelUIModel> =
        MutableLiveData(MainViewModelUIModel.ShowBottomBar)

    var selected: RepositoriesTypeUiModel = RepositoriesTypeUiModel.DAILY

    val mainUiChanges: LiveData<MainViewModelUIModel> get() = _main

    fun onDetailEntered() = _main.postValue(MainViewModelUIModel.HideBottomBar)

    fun onListEntered() = _main.postValue(MainViewModelUIModel.ShowBottomBar)
}
