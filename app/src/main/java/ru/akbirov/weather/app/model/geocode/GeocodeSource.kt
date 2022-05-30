package ru.akbirov.weather.app.model.geocode

import ru.akbirov.weather.app.model.geocode.entities.City

interface GeocodeSource {

    suspend fun searchCity(name: String, limit: Int?): List<City>
}