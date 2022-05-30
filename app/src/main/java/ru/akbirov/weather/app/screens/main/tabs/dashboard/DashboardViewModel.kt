package ru.akbirov.weather.app.screens.main.tabs.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.*
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.WeatherRepository
import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.HourForecast
import ru.akbirov.weather.app.screens.base.BaseViewModel
import ru.akbirov.weather.app.utils.share

class DashboardViewModel(
    private val weatherRepository: WeatherRepository = Singletons.weatherRepository,
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository
) : BaseViewModel() {

    private val _viewState = MutableLiveData<Result<ViewState>>(Pending())
    val viewState: LiveData<Result<ViewState>> = _viewState.share()

    init {
        viewModelScope.safeLaunch {
            requestData()

            settingsRepository.listenSettingsChange().collect {
                requestData()
            }
        }
    }

    private suspend fun requestData() {
        coroutineScope {
            try {
                val currentDataDeferred = async {
                    requestCurrentData()
                }

                val hourlyForecastDeferred = async {
                    requestHourlyForecast()
                }

                _viewState.postValue(
                    Success(
                        ViewState(
                            currentData = currentDataDeferred.await(),
                            currentHourlyForecast = hourlyForecastDeferred.await()
                        )
                    )
                )
            } catch (e: EmptyCoordinates) {
                _viewState.postValue(Error(e))
            }
        }
    }

    fun reload(onFinished: () -> Unit) {
        viewModelScope.safeLaunch {
            try {
                requestData()
            } finally {
                onFinished()
            }
        }
    }

    private suspend fun requestCurrentData(): CurrentData {
        if (settingsRepository.getCurrentCoordinate() == null) throw EmptyCoordinates()

        return weatherRepository.getCurrentDate()
    }

    private suspend fun requestHourlyForecast(): HourForecast {
        if (settingsRepository.getCurrentCoordinate() == null) throw EmptyCoordinates()

        return weatherRepository.getHourlyForecast(5)
    }


    data class ViewState(
        val currentData: CurrentData,
        val currentHourlyForecast: HourForecast
    )
}