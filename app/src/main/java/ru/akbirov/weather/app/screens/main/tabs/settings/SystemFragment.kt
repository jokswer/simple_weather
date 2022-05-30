package ru.akbirov.weather.app.screens.main.tabs.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import ru.akbirov.weather.R
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.databinding.FragmentSystemBinding

class SystemFragment : BaseFragment(R.layout.fragment_system) {

    override val viewModel by viewModels<SystemViewModel>()

    private lateinit var binding: FragmentSystemBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSystemBinding.bind(view)

        setupLanguagesList()
        setupUnitsList()
    }

    private fun setupLanguagesList() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            viewModel.languages.map { it.name }
        )

        binding.languageSpinner.adapter = adapter

        val index = viewModel.getCurrentLanguageIndex()
        if (index != null) binding.languageSpinner.setSelection(index, false)

        binding.languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setCurrentLanguage(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }
    }

    private fun setupUnitsList() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            viewModel.units.map { it.name }
        )

        binding.unitSpinner.adapter = adapter

        val index = viewModel.getCurrentUnitIndex()
        if (index != null) binding.unitSpinner.setSelection(index, false)

        binding.unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                viaew: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setCurrentUnit(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}