package ru.akbirov.weather.app.screens.main.tabs.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.akbirov.weather.R
import ru.akbirov.weather.app.model.Pending
import ru.akbirov.weather.app.model.Success
import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.ListItem
import ru.akbirov.weather.app.screens.base.BaseFragment
import ru.akbirov.weather.app.utils.DISPLAY_FORMAT
import ru.akbirov.weather.app.utils.getDate
import ru.akbirov.weather.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: DashboardAdapter

    override val viewModel: DashboardViewModel by viewModels<DashboardViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDashboardBinding.bind(view)

        adapter = DashboardAdapter()

        binding.moreButton.setOnClickListener {
            onMorePress()
        }

        binding.setCoordinateButton.setOnClickListener { onSetLocationPress() }

        binding.pullToRefresh.setOnRefreshListener {
            viewModel.reload {
                binding.pullToRefresh.isRefreshing = false
            }
        }

        viewModel.viewState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Success -> result.getValueOrNull()?.let { renderCard(it) }
                is Pending -> renderLoading()
                else -> renderError()
            }
        }
    }

    private fun onMorePress() {
        findNavController().navigate(R.id.action_dashboardFragment_to_detailsFragment)
    }

    private fun onSetLocationPress() {
        findNavController().navigate(R.id.action_dashboardFragment_to_locationFragment2)
    }

    private fun renderCard(data: DashboardViewModel.ViewState) {
        with(binding) {
            progress.visibility = View.GONE
            weatherCV.visibility = View.VISIBLE
            detailsCV.visibility = View.VISIBLE
            setCoordinateButton.visibility = View.GONE
        }

        renderWeatherCard(data.currentData)
        renderDetailsCard(data.currentHourlyForecast.list)
    }

    private fun renderWeatherCard(currentData: CurrentData) {
        with(binding) {
            cityTV.text = currentData.name
            timeTV.text = getDate(currentData.dt, DISPLAY_FORMAT)
            temperatureTV.text = getString(R.string.temperature, currentData.main.temp)
            feelsLikeTV.text = getString(R.string.feels_like, currentData.main.feels_like)
            windTV.text = getString(R.string.wind, currentData.wind.speed)
            humidityTV.text = getString(R.string.humidity, currentData.main.humidity)
            descriptionTV.text = currentData.weather[0].description
        }
    }

    private fun renderDetailsCard(items: List<ListItem>) {
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.detailsList.layoutManager = layoutManager
        binding.detailsList.adapter = adapter

        adapter.details = items
    }

    private fun renderLoading() {
        with(binding) {
            progress.visibility = View.VISIBLE
            weatherCV.visibility = View.GONE
            detailsCV.visibility = View.GONE
            setCoordinateButton.visibility = View.GONE
        }
    }

    private fun renderError() {
        with(binding) {
            progress.visibility = View.GONE
            weatherCV.visibility = View.GONE
            detailsCV.visibility = View.GONE
            setCoordinateButton.visibility = View.VISIBLE
        }
    }
}