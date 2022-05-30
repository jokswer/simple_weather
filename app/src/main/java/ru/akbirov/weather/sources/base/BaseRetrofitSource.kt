package ru.akbirov.weather.sources.base

import retrofit2.Retrofit
import java.io.IOException

open class BaseRetrofitSource(
    private val config: RetrofitConfig
) {
    val retrofit: Retrofit = config.retrofit
    private val gson = config.gson

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        }  catch (e: IOException) {
            throw e
        }
    }
}