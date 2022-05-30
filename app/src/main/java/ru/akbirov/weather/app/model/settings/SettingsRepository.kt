package ru.akbirov.weather.app.model.settings

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import ru.akbirov.weather.app.model.appSettings.AppSettings
import ru.akbirov.weather.app.model.weather.entities.Coord

typealias SettingsListener = () -> Unit

class SettingsRepository(
    private val appSettings: AppSettings
) {

    private val listeners = mutableSetOf<SettingsListener>()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun listenSettingsChange(): Flow<Unit> = callbackFlow<Unit> {
        val listener: SettingsListener = {
            trySend(Unit)
        }

        listeners.add(listener)

        awaitClose {
            listeners.remove(listener)
        }
    }.buffer(Channel.CONFLATED)

    fun setCurrentLanguage(code: String) {
        appSettings.setLanguage(code)
        listeners.forEach { it() }
    }

    fun getCurrentLanguage() = appSettings.getLanguage()

    fun setCurrentUnit(unit: String) {
        appSettings.setUnit(unit)
        listeners.forEach { it() }
    }

    fun getCurrentUnit() = appSettings.getUnit()

    fun setCurrentCoordinate(coord: Coord) {
        appSettings.setCoordinate(coord)
        listeners.forEach { it() }
    }

    fun getCurrentCoordinate() = appSettings.getCoordinate()
}