package ru.akbirov.weather.sources.base

import com.google.gson.Gson
import retrofit2.Retrofit

class RetrofitConfig (
    val retrofit: Retrofit,
    val gson: Gson
)