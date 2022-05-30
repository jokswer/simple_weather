package ru.akbirov.weather.sources.geocode

import retrofit2.http.GET
import retrofit2.http.Query
import ru.akbirov.weather.app.model.geocode.entities.City

interface GeocodeApi {

    @GET("direct")
    suspend fun searchCity(@Query("q") q: String, @Query("limit") limit: Int?): List<City>
}