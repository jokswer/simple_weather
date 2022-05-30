package ru.akbirov.weather.sources.base

import ru.akbirov.weather.app.model.SourcesProvider
import ru.akbirov.weather.app.model.geocode.GeocodeSource
import ru.akbirov.weather.app.model.weather.WeatherSource
import ru.akbirov.weather.sources.geocode.RetrofitGeocodeSource
import ru.akbirov.weather.sources.weather.RetrofitWeatherSource

class RetrofitSourcesProvider(
    private val weatherConfig: RetrofitConfig,
    private val geocodeConfig: RetrofitConfig,
) : SourcesProvider {
    override fun getWeatherSource(): WeatherSource {
        return RetrofitWeatherSource(weatherConfig)
    }

    override fun getGeocodeSource(): GeocodeSource {
        return RetrofitGeocodeSource(geocodeConfig)
    }
}