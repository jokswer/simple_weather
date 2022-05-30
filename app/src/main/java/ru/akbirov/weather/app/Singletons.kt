package ru.akbirov.weather.app

import android.content.Context
import ru.akbirov.weather.app.model.SourcesProvider
import ru.akbirov.weather.app.model.appSettings.AppSettings
import ru.akbirov.weather.app.model.appSettings.SharedPreferencesAppSettings
import ru.akbirov.weather.app.model.geocode.GeocodeRepository
import ru.akbirov.weather.app.model.geocode.GeocodeSource
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.model.weather.WeatherRepository
import ru.akbirov.weather.app.model.weather.WeatherSource
import ru.akbirov.weather.sources.SourceProviderHolder

object Singletons {

    private lateinit var appContext: Context

    val appSettings: AppSettings by lazy {
        SharedPreferencesAppSettings(appContext)
    }

    private val sourcesProvider: SourcesProvider by lazy {
        SourceProviderHolder.sourcesProvider
    }

    private val weatherSource: WeatherSource by lazy {
        sourcesProvider.getWeatherSource()
    }

    private val geocodeSource: GeocodeSource by lazy {
        sourcesProvider.getGeocodeSource()
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherSource = weatherSource)
    }

    val geocodeRepository: GeocodeRepository by lazy {
        GeocodeRepository(geocodeSource = geocodeSource)
    }

    val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(appSettings = appSettings)
    }

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}