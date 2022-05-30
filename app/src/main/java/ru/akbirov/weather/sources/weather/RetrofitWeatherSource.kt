package ru.akbirov.weather.sources.weather

import ru.akbirov.weather.app.model.weather.WeatherSource
import ru.akbirov.weather.app.model.weather.entities.CurrentData
import ru.akbirov.weather.app.model.weather.entities.HourForecast
import ru.akbirov.weather.sources.base.BaseRetrofitSource
import ru.akbirov.weather.sources.base.RetrofitConfig

class RetrofitWeatherSource(config: RetrofitConfig) : BaseRetrofitSource(config), WeatherSource {
    private val weatherApi = retrofit.create(WeatherApi::class.java)

    override suspend fun getCurrentData(): CurrentData {
        return wrapRetrofitExceptions {
            weatherApi.getCurrentData()
        }
    }

    override suspend fun getHourlyForecast(cnt: Int?): HourForecast {
        return wrapRetrofitExceptions {
            weatherApi.getHourlyForecast(cnt)
        }
    }
}