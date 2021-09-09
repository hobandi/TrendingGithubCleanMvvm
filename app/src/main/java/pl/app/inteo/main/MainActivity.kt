package pl.app.inteo.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.app.inteo.R
import pl.app.inteo.databinding.ActivityMainBinding
import pl.app.inteo.presentation.extension.slideDown
import pl.app.inteo.presentation.extension.slideUp
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.viewmodel.MainViewModel
import pl.app.inteo.presentation.viewmodel.MainViewModelUIModel


class MainActivity : AppCompatActivity(), LifecycleObserver, NavController.OnDestinationChangedListener {


    private val mainViewModel: MainViewModel by viewModel()

    private var binding: ActivityMainBinding? = null

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupBottomNavigationBar()
        setupToolbar()
        setupViewModel()
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        toolbar = binding?.toolbar
    }

    private fun setupViewModel() = mainViewModel.mainUiChanges.observe(this, {
        when (it) {
            MainViewModelUIModel.HideBottomBar -> binding?.bottomNavigationView?.slideDown()
            MainViewModelUIModel.ShowBottomBar -> binding?.bottomNavigationView?.slideUp()
        }
    })

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() = binding?.bottomNavigationView?.run {
        NavigationUI.setupWithNavController(this, findNavController(R.id.navHostFragment))
        findNavController(R.id.navHostFragment).addOnDestinationChangedListener(
            this@MainActivity
        )
    }

    fun onRepositoriesTypeUiModelSelected(selectedFromFragment: RepositoriesTypeUiModel) {
        mainViewModel.selected = selectedFromFragment
    }

    fun getSelectedRepositoriesTypeUiModel() = mainViewModel.selected

    private fun setupToolbar() = binding?.toolbar?.apply {
        setNavigationIcon(R.drawable.ic_github)
        setSupportActionBar(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when (destination.id) {
            R.id.trendingDetail -> {
                mainViewModel.onDetailEntered()
            }
            R.id.trendingList, R.id.bookmarked -> {
                mainViewModel.onListEntered()
            }
        }
    }

}
