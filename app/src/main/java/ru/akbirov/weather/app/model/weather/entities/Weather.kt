package ru.akbirov.weather.app.model.weather.entities

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)
