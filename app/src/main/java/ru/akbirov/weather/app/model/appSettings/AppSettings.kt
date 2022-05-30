package ru.akbirov.weather.app.model.appSettings

import ru.akbirov.weather.app.model.weather.entities.Coord

interface AppSettings {

    fun setLanguage(code: String)
    fun getLanguage(): String?

    fun setUnit(unit: String)
    fun getUnit(): String?

    fun setCoordinate(coord: Coord)
    fun getCoordinate(): Coord?
}