package ru.akbirov.weather.app.model

import ru.akbirov.weather.app.model.geocode.GeocodeSource
import ru.akbirov.weather.app.model.weather.WeatherSource

interface SourcesProvider {
    fun getWeatherSource(): WeatherSource

    fun getGeocodeSource(): GeocodeSource
}