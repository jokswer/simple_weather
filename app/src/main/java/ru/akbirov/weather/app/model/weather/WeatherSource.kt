package ru.akbirov.weather.app.model.weather

import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.HourForecast

interface WeatherSource {

    suspend fun getCurrentData(): CurrentData

    suspend fun getHourlyForecast(cnt: Int?): HourForecast
}