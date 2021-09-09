package pl.app.inteo.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.app.inteo.base.BaseFragment
import pl.app.inteo.databinding.SettingsFragmentBinding
import pl.app.inteo.presentation.extension.hide
import pl.app.inteo.presentation.extension.show
import pl.app.inteo.presentation.interfaces.ChangeTheme
import pl.app.inteo.presentation.viewmodel.BaseViewModel
import pl.app.inteo.presentation.viewmodel.SettingUIModel
import pl.app.inteo.presentation.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment<SettingsFragmentBinding, BaseViewModel>() {

    override val viewModel: SettingsViewModel by viewModel()

    private val changeTheme: ChangeTheme by inject()

    private var settingsAdapter: SettingsAdapter = SettingsAdapter()

    override fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?) =
        SettingsFragmentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.settings.observe(viewLifecycleOwner, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getSettings()
        binding.error.errorRetryButton.setOnClickListener { viewModel.getSettings() }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        settingsAdapter.setItemClickListener { selectedSetting, _ ->
            viewModel.setSettings(selectedSetting)
        }
    }

    private fun onViewStateChange(result: SettingUIModel) {
        when (result) {
            is SettingUIModel.NightMode -> {
                result.nightMode.let {
                    changeTheme.setNightMode(it)
                }
            }
            is SettingUIModel.Success -> {
                binding.error.root.hide()
                result.data.let {
                    settingsAdapter.list = it
                }
            }
            is SettingUIModel.Error -> {
                binding.error.errorTitle.text = getString(result.messageRes)
                binding.error.root.show()
            }
        }
    }
}
