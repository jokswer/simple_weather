package ru.akbirov.weather.app.screens.main.tabs.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.Error
import ru.akbirov.weather.app.model.Pending
import ru.akbirov.weather.app.model.Result
import ru.akbirov.weather.app.model.Success
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.WeatherRepository
import ru.akbirov.weather.app.model.weather.entities.HourForecast
import ru.akbirov.weather.app.screens.base.BaseViewModel
import ru.akbirov.weather.app.utils.share

class DetailsViewModel(
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository,
    private val weatherRepository: WeatherRepository = Singletons.weatherRepository,
) : BaseViewModel() {

    private val _currentHourlyForecast = MutableLiveData<Result<HourForecast>>(Pending())
    val currentHourlyForecast = _currentHourlyForecast.share()

    init {
        viewModelScope.safeLaunch {
            requestHourlyForecast()

            settingsRepository.listenSettingsChange().collect {
                requestHourlyForecast()
            }
        }
    }

    private suspend fun requestHourlyForecast() {
        try {
            val result = weatherRepository.getHourlyForecast(null)
            _currentHourlyForecast.postValue(Success(result))
        } catch (e: Exception) {
            _currentHourlyForecast.postValue(Error(e))
        }
    }

    fun reload(onFinished: () -> Unit) {
        viewModelScope.safeLaunch {
            requestHourlyForecast()
            onFinished()
        }
    }
}