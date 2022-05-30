package ru.akbirov.weather.app.screens.main.tabs.settings

import androidx.lifecycle.MutableLiveData
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.entities.Coord
import ru.akbirov.weather.app.screens.base.BaseViewModel
import ru.akbirov.weather.app.utils.LiveEvent
import ru.akbirov.weather.app.utils.MutableLiveEvent
import ru.akbirov.weather.app.utils.publishEvent
import ru.akbirov.weather.app.utils.share

class MapViewModel(
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository
) : BaseViewModel() {

    private val _coordinateIsInit = MutableLiveEvent<Coord>()
    val coordinateIsInit = _coordinateIsInit.share()

    private val _selectedCoordinate = MutableLiveData<Coord?>(null)
    val selectedCoordinate = _selectedCoordinate.share()

    init {
        val coordinate = settingsRepository.getCurrentCoordinate()
        coordinate?.let {
            _coordinateIsInit.publishEvent(it)
        }
    }

    fun onSelect(lon: Double, lat: Double) {
        _selectedCoordinate.postValue(Coord(lon, lat))
    }

    fun onSave() {
        _selectedCoordinate.value?.let {
            settingsRepository.setCurrentCoordinate(it)
            onSuccess(null)
        }
    }
}