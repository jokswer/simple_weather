package ru.akbirov.weather.app.screens.main.tabs.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.Empty
import ru.akbirov.weather.app.model.Pending
import ru.akbirov.weather.app.model.Result
import ru.akbirov.weather.app.model.Success
import ru.akbirov.weather.app.model.geocode.GeocodeRepository
import ru.akbirov.weather.app.model.geocode.entities.City
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.entities.Coord
import ru.akbirov.weather.app.screens.base.BaseViewModel
import ru.akbirov.weather.app.utils.share

class SearchViewModel(
    private val geocodeRepository: GeocodeRepository = Singletons.geocodeRepository,
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository
) : BaseViewModel() {

    private val _cities = MutableSharedFlow<Result<List<City>>>()
    val cities: SharedFlow<Result<List<City>>> = _cities

    private val _selectedCity = MutableLiveData<City?>()
    val selectedCity = _selectedCity.share()

    private val _searchedName = MutableSharedFlow<String>()

    init {
        viewModelScope.safeLaunch {
            _searchedName.debounce(800).collect {
                if (it.isBlank()) return@collect
                searchCity(it)
            }
        }
    }

    fun setSearchedName(name: String) {
        viewModelScope.launch {
            _searchedName.emit(name)
        }
    }

    fun setSelectedCity(city: City) {
        _selectedCity.postValue(city)
    }

    fun onSave() {
        _selectedCity.value?.let {
            val coordinates = Coord(
                lon = it.lon,
                lat = it.lat
            )
            settingsRepository.setCurrentCoordinate(coordinates)
            onSuccess(null)
        }
    }

    private suspend fun searchCity(name: String) {
        try {
            _cities.emit(Pending())
            val result = geocodeRepository.searchCity(name)
            if (result.isEmpty()) _cities.emit(Empty()) else _cities.emit(Success(result))
        } catch (e: HttpException) {
            _cities.emit(Empty())
            throw e
        }
    }
}