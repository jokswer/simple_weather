package ru.akbirov.weather.app.model.weather.entities

data class CurrentData(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val code: Int
)

data class Wind(
    val speed: Number,
    val deg: Int
)

data class Clouds(
    val all: Int
)

data class Sys(
    val type: Int,
    val id: Int,
    val message: Number,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)

data class Main(
    val temp: Number,
    val feels_like: Number,
    val temp_min: Number,
    val temp_max: Number,
    val pressure: Int,
    val humidity: Int
)