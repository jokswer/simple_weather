package ru.akbirov.weather.sources.weather

import retrofit2.http.GET
import retrofit2.http.Query
import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.HourForecast

interface WeatherApi {

    @GET("weather")
    suspend fun getCurrentData(): CurrentData

    @GET("forecast")
    suspend fun getHourlyForecast(@Query("cnt") cnt: Int?): HourForecast
}