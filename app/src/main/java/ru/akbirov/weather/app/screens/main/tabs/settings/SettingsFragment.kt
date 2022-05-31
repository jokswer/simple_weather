package ru.akbirov.weather.app.screens.main.tabs.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.common.Setting
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override val viewModel by viewModels<SettingsViewModel>()

    private lateinit var adapter: SettingsAdapter

    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)

        setupList(viewModel.settings)
    }

    private fun setupList(settings: List<Setting>) {
        adapter = SettingsAdapter(settings)
        binding.settingsListView.adapter = adapter

        binding.settingsListView.setOnItemClickListener { parent, view, position, id ->
            onNavigate(adapter.getItem(position).id)
        }
    }

    private fun onNavigate(id: Long) {
        when (id) {
            SettingsViewModel.LOCATION -> findNavController().navigate(
                R.id.action_settingsFragment_to_locationFragment, null,
                getNavOptions()
            )
            SettingsViewModel.SYSTEM -> findNavController().navigate(
                R.id.action_settingsFragment_to_systemFragment, null,
                getNavOptions()
            )
        }
    }
}