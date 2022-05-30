package ru.akbirov.weather.app.screens.main

import androidx.lifecycle.ViewModel
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.entities.Coord
import ru.akbirov.weather.app.utils.MutableLiveEvent
import ru.akbirov.weather.app.utils.publishEvent
import ru.akbirov.weather.app.utils.share

class MainViewModel(
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository
) : ViewModel() {

    private val _currentCoordinate = MutableLiveEvent<Coord?>(null)
    val currentCoordinate = _currentCoordinate.share()

    init {
        val coordinate = settingsRepository.getCurrentCoordinate()
        coordinate?.let {
            _currentCoordinate.publishEvent(it)
        }
    }

    fun saveCoordinates(lon: Double, lat: Double) {
        val coordinates = Coord(lon, lat)
        settingsRepository.setCurrentCoordinate(coordinates)
    }

}