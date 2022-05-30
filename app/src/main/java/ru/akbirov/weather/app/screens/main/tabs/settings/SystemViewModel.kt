package ru.akbirov.weather.app.screens.main.tabs.settings

import ru.akbirov.weather.app.Singletons
import ru.akbirov.weather.app.model.common.Language
import ru.akbirov.weather.app.model.common.Unit
import ru.akbirov.weather.app.model.settings.SettingsRepository
import ru.akbirov.weather.app.screens.base.BaseViewModel

class SystemViewModel(
    private val settingsRepository: SettingsRepository = Singletons.settingsRepository
) : BaseViewModel() {

    val languages = listOf(
        Language("English", "en"),
        Language("German", "de"),
        Language("Russian", "ru"),
        Language("Ukrainian", "uk"),
    )

    val units = listOf(
        Unit("Standard"),
        Unit("Metric"),
        Unit("Imperial")
    )

    fun setCurrentLanguage(position: Int) {
        val language = languages[position]
        settingsRepository.setCurrentLanguage(language.code)
    }

    fun getCurrentLanguageIndex(): Int? {
        val language = settingsRepository.getCurrentLanguage()

        if (language != null) {
            return languages.indexOfFirst { it.code == language }
        }

        return null
    }

    fun setCurrentUnit(position: Int) {
        val unit = units[position]
        settingsRepository.setCurrentUnit(unit.name)
    }

    fun getCurrentUnitIndex(): Int? {
        val unit = settingsRepository.getCurrentUnit()

        if (unit != null) {
            return units.indexOfFirst { it.name == unit }
        }

        return null
    }
}