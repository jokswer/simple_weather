package ru.akbirov.weather.app.screens.main.tabs.settings

import ru.akbirov.weather.app.model.common.Setting
import ru.akbirov.weather.app.screens.base.BaseViewModel

class SettingsViewModel : BaseViewModel() {
    val settings = listOf(
        Setting(LOCATION, "Location", null),
        Setting(SYSTEM, "System", null)
    )

    companion object SettingsNames {
        const val LOCATION = 1L;
        const val SYSTEM = 2L;
    }
}

