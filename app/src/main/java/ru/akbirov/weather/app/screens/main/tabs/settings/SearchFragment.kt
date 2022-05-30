package ru.akbirov.weather.app.screens.main.tabs.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.Pending
import ru.akbirov.weather.app.model.Success
import ru.akbirov.weather.app.model.geocode.entities.City
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.databinding.FragmentSearchBinding

class SearchFragment() : BaseFragment(R.layout.fragment_search) {

    override val viewModel by viewModels<SearchViewModel>()

    private lateinit var adapter: SearchAdapter
    private lateinit var binding: FragmentSearchBinding

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding.loading.visibility = View.GONE

        binding.searchTextInputEditText.addTextChangedListener {
            viewModel.setSearchedName(it.toString())

            binding.saveLocationButton.isEnabled = false
            adapter.selectedItemPosition = null
        }

        binding.saveLocationButton.setOnClickListener {
            viewModel.onSave()
        }

        setupAdapter()
        setupList()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cities.collect { result ->
                    when (result) {
                        is Pending -> renderLoading()
                        is Success -> renderList(result.getValueOrNull())
                        else -> renderEmpty()
                    }
                }
            }
        }

        viewModel.selectedCity.observe(viewLifecycleOwner) { city ->
            binding.saveLocationButton.isEnabled = city != null
        }
    }

    private fun onCitySelect(city: City) {
        viewModel.setSelectedCity(city)
    }

    private fun setupAdapter() {
        adapter = SearchAdapter(onCityClick = {
            onCitySelect(it)
        })
    }

    private fun setupList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    private fun renderLoading() {
        with(binding) {
            emptyTV.visibility = View.GONE
            list.visibility = View.INVISIBLE
            loading.visibility = View.VISIBLE
        }
    }

    private fun renderList(cities: List<City>?) {
        adapter.cities = cities ?: emptyList()

        with(binding) {
            emptyTV.visibility = View.GONE
            list.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }
    }

    private fun renderEmpty() {
        with(binding) {
            emptyTV.visibility = View.VISIBLE
            list.visibility = View.INVISIBLE
            loading.visibility = View.GONE
        }
    }
}