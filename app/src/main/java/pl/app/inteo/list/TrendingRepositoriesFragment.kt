package pl.app.inteo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.app.inteo.R
import pl.app.inteo.base.BaseFragment
import pl.app.inteo.databinding.TrendingRepositoriesFragmentBinding
import pl.app.inteo.extenstions.mapSortingToPresentation
import pl.app.inteo.extenstions.mapToPresentation
import pl.app.inteo.main.MainActivity
import pl.app.inteo.presentation.extension.hide
import pl.app.inteo.presentation.extension.show
import pl.app.inteo.presentation.model.RepositoriesTypeUiModel
import pl.app.inteo.presentation.model.RepositoryUiModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoriesUIModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoriesViewModel

class TrendingRepositoriesFragment :
    BaseFragment<TrendingRepositoriesFragmentBinding, TrendingRepositoriesViewModel>() {

    override val viewModel: TrendingRepositoriesViewModel by viewModel()

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?) =
        TrendingRepositoriesFragmentBinding.inflate(inflater, container, false)

    private val repositoriesAdapter = RepositoriesAdapter()

    private val args: TrendingRepositoriesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup(view)
        setHasOptionsMenu(true)
        initRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        val selected = (activity as MainActivity).getSelectedRepositoriesTypeUiModel()
        if (args.bookmarkedList) {
            viewModel.loadBookmarkedList(selected)
        } else {
            viewModel.loadRepositoryList(selected)
        }
        viewModel.repositoryList.observe(viewLifecycleOwner, ::onViewStateChange)
    }

    private fun setup(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        activity?.title = getString(R.string.trending_github_title)
        binding.swipeRepoRefresh.setOnRefreshListener {
            forceRefresh(viewModel.selectedRepositoriesType)
        }
        binding.error.errorRetryButton.setOnClickListener { forceRefresh(viewModel.selectedRepositoriesType) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(getMenuConfig(), menu)

    private fun getMenuConfig() = if (args.bookmarkedList)
        R.menu.menu_bookmarked_toolbar
    else
        R.menu.menu_list_toolbar

    private fun initRecyclerView() {
        binding.repositoryRecycleView.apply {
            adapter = repositoriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        repositoriesAdapter.setItemClickListener { repository, view ->
            onRepositoryClicked(view, repository)
        }
    }

    private fun onRepositoryClicked(
        view: View,
        repository: RepositoryUiModel
    ) {
        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(
                R.integer.reply_motion_duration_large
            ).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(
                R.integer.reply_motion_duration_large
            ).toLong()
        }
        val emailCardDetailTransitionName = getString(
            R.string.detail_transition_name
        )
        val extras = FragmentNavigatorExtras(
            view to emailCardDetailTransitionName
        )
        val directions = TrendingRepositoriesFragmentDirections.actionTrendingListToTrendingDetail(
            repository.repositoryName,
            viewModel.selectedRepositoriesType
        )
        findNavController().navigate(directions, extras)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_today, R.id.menu_week, R.id.menu_month -> {
                onMenuChangeRepositorySinceType(item)
                true
            }
            R.id.menu_sort_fork, R.id.menu_sort_turn_off, R.id.menu_sort_total_stars, R.id.menu_sort_stars_since -> {
                onMenuChangeSortingType(item)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun onMenuChangeRepositorySinceType(item: MenuItem): Boolean = item.mapToPresentation().let {
        viewModel.selectedRepositoriesType = it
        (activity as MainActivity).onRepositoriesTypeUiModelSelected(it)
        forceRefresh(it)
        true
    }

    private fun onMenuChangeSortingType(item: MenuItem): Boolean = item.mapSortingToPresentation().let {
        viewModel.selectedSortedType = it
        repositoriesAdapter.list = emptyList()
        viewModel.loadSortedRepository(it)
        true
    }

    private fun forceRefresh(repositoriesTypeUiModel: RepositoriesTypeUiModel) {
        if (args.bookmarkedList) {
            viewModel.loadBookmarkedList(viewModel.selectedRepositoriesType, true)
        } else {
            viewModel.loadRepositoryList(repositoriesTypeUiModel, true)
        }
        repositoriesAdapter.list = emptyList()
    }

    private fun onViewStateChange(event: TrendingRepositoriesUIModel) {
        when (event) {
            is TrendingRepositoriesUIModel.Error -> {
                binding.error.errorTitle.text = getString(event.messageRes)
                binding.error.root.show()
            }
            is TrendingRepositoriesUIModel.Loading -> {
                binding.loading.show()
            }
            is TrendingRepositoriesUIModel.Success -> {
                binding.error.root.hide()
                setupOnSuccess(event)
            }
        }
    }

    private fun setupOnSuccess(event: TrendingRepositoriesUIModel.Success) {
        binding.swipeRepoRefresh.isRefreshing = false
        binding.loading.hide()
        event.data.let {
            repositoriesAdapter.apply {
                list = it
            }
        }
    }
}
