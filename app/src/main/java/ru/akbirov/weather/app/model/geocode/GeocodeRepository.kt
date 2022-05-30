package ru.akbirov.weather.app.model.geocode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.akbirov.weather.app.model.geocode.entities.City

class GeocodeRepository(
    private val geocodeSource: GeocodeSource
) {

    suspend fun searchCity(name: String, limit: Int = 5): List<City> = withContext(Dispatchers.IO) {
        return@withContext geocodeSource.searchCity(name, limit)
    }
}