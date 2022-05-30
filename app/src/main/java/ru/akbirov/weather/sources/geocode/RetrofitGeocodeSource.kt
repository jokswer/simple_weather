package ru.akbirov.weather.sources.geocode

import ru.akbirov.weather.app.model.geocode.GeocodeSource
import ru.akbirov.weather.app.model.geocode.entities.City
import ru.akbirov.weather.sources.base.BaseRetrofitSource
import ru.akbirov.weather.sources.base.RetrofitConfig

class RetrofitGeocodeSource(config: RetrofitConfig) : BaseRetrofitSource(config), GeocodeSource {
    private val geocodeApi = retrofit.create(GeocodeApi::class.java)

    override suspend fun searchCity(name: String, limit: Int?): List<City> {
        return wrapRetrofitExceptions {
            geocodeApi.searchCity(name, limit)
        }
    }
}