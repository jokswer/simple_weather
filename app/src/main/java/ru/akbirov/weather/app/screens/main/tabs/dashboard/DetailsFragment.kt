package ru.akbirov.weather.app.screens.main.tabs.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.Pending
import ru.akbirov.weather.app.model.Success
import ru.akbirov.weather.app.model.weather.entities.HourForecast
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.databinding.FragmentDetailsBinding

class DetailsFragment : BaseFragment(R.layout.fragment_details) {

    override val viewModel by viewModels<DetailsViewModel>()

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var adapter: DetailsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        binding.pullToRefresh.setOnRefreshListener {
            viewModel.reload {
                binding.pullToRefresh.isRefreshing = false
            }
        }

        setupAdapter()
        setupList()

        viewModel.currentHourlyForecast.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> renderList(it.getValueOrNull())
                is Pending -> renderLoading()
                else -> renderEmpty()
            }
        }
    }

    private fun setupAdapter() {
        adapter = DetailsAdapter()
    }

    private fun setupList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    private fun renderList(hourlyForecast: HourForecast?) {
        adapter.details = hourlyForecast?.list ?: emptyList()

        binding.list.visibility = View.VISIBLE
        binding.loading.visibility = View.GONE
        binding.noResultTV.visibility = View.GONE
    }

    private fun renderLoading() {
        binding.list.visibility = View.INVISIBLE
        binding.loading.visibility = View.VISIBLE
        binding.noResultTV.visibility = View.GONE
    }

    private fun renderEmpty() {
        binding.list.visibility = View.INVISIBLE
        binding.loading.visibility = View.GONE
        binding.noResultTV.visibility = View.VISIBLE
    }
}