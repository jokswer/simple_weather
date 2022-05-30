package ru.akbirov.weather.app.model.appSettings

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.akbirov.weather.app.model.weather.entities.Coord

class SharedPreferencesAppSettings(appContext: Context) : AppSettings {

    private val gson = Gson()

    private val sharedPreferences =
        appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun setLanguage(code: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_LANGUAGE, code)
        editor.apply()
    }

    override fun getLanguage(): String? {
        return sharedPreferences.getString(PREF_LANGUAGE, null)
    }

    override fun setUnit(unit: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_UNIT, unit)
        editor.apply()
    }

    override fun getUnit(): String? {
        return sharedPreferences.getString(PREF_UNIT, null)
    }

    override fun setCoordinate(coord: Coord) {
        val editor = sharedPreferences.edit()
        editor.putString(PREF_COORDINATE, gson.toJson(coord))
        editor.apply()
    }

    override fun getCoordinate(): Coord? {
        return try {
            val coordinateJson = sharedPreferences.getString(PREF_COORDINATE, null)
            gson.fromJson(coordinateJson, Coord::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    companion object {
        private const val PREF_LANGUAGE = "language"
        private const val PREF_UNIT = "unit"
        private const val PREF_COORDINATE = "coordinate"
    }
}