package ru.akbirov.weather.app.model.geocode.entities

data class City(
    val name: String,
    val local_names: Map<String, String>,
    val lat: Number,
    val lon: Number,
    val country: String,
    val state: String
)
