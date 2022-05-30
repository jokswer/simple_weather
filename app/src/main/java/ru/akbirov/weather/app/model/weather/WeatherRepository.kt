package ru.akbirov.weather.app.model.weather

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.HourForecast

class WeatherRepository(
    private val weatherSource: WeatherSource
) {

    suspend fun getCurrentDate(): CurrentData = withContext(Dispatchers.IO) {
        return@withContext weatherSource.getCurrentData()
    }

    suspend fun getHourlyForecast(cnt: Int?): HourForecast = withContext(Dispatchers.IO) {
        return@withContext weatherSource.getHourlyForecast(cnt)
    }

}