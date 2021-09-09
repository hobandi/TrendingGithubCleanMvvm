package pl.app.inteo.detail

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.app.inteo.MainNavigationDirections
import pl.app.inteo.R
import pl.app.inteo.base.BaseFragment
import pl.app.inteo.databinding.TrendingRepositoriesDetailFragmentBinding
import pl.app.inteo.presentation.extension.browse
import pl.app.inteo.presentation.extension.hide
import pl.app.inteo.presentation.extension.show
import pl.app.inteo.presentation.extension.themeColor
import pl.app.inteo.presentation.viewmodel.BookmarkDetailViewModelUIModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModelUIModel
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModelUIModel.Error
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModelUIModel.Loading
import pl.app.inteo.presentation.viewmodel.TrendingRepositoryDetailViewModelUIModel.Success

class TrendingRepositoryDetailFragment :
    BaseFragment<TrendingRepositoriesDetailFragmentBinding, TrendingRepositoryDetailViewModel>() {

    private val args: TrendingRepositoryDetailFragmentArgs by navArgs()

    override val viewModel: TrendingRepositoryDetailViewModel by viewModel {
        parametersOf(
            args.repositoryName,
            args.repositoriesType
        )
    }

    private val builtByAdapter = BuiltByAdapter()

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?) =
        TrendingRepositoriesDetailFragmentBinding.inflate(inflater, container, false)

    private val onBackCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() = findNavController().navigate(
            MainNavigationDirections.actionTrendingList(false)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animationSetup()
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackCallback)
    }

    private fun animationSetup() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostFragment
            duration = resources.getInteger(
                R.integer.reply_motion_duration_large
            ).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        viewModel.loadRepositoryAndBookmark()
        viewModel.repository.observe(viewLifecycleOwner, ::onViewStateChange)
        viewModel.bookmark.observe(viewLifecycleOwner, ::onViewStateChange)
        binding.error.errorRetryButton.setOnClickListener { viewModel.loadRepositoryAndBookmark() }
    }

    private fun setup() {
        activity?.title = getString(R.string.trending_github_title)

        with(binding) {
            builtRV.apply {
                binding.builtRV.adapter = builtByAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            bookmark.setOnClickListener {
                viewModel.setBookmark(!it.isSelected)
            }
        }
    }

    private fun onViewStateChange(event: BookmarkDetailViewModelUIModel) {
        when (event) {
            is BookmarkDetailViewModelUIModel.SuccessGetBookmark -> {
                binding.bookmark.isSelected = event.bookmark
            }
            is BookmarkDetailViewModelUIModel.SuccessSetBookmark -> {
                binding.bookmark.isSelected = event.bookmark
            }
        }
    }

    private fun onViewStateChange(event: TrendingRepositoryDetailViewModelUIModel) {
        when (event) {
            is Loading -> {
            }
            is Success -> {
                binding.error.root.hide()
                onSuccess(event)
            }
            is Error -> {
                binding.error.errorTitle.text = getString(event.messageRes)
                binding.error.root.show()
            }
        }
    }

    private fun onSuccess(event: Success) {
        with(binding) {
            repository = event.data
            builtByAdapter.list = event.data.builtBy
            openRepository.setOnClickListener { it?.context?.browse(event.data.url) }
            executePendingBindings()
        }
    }
}
