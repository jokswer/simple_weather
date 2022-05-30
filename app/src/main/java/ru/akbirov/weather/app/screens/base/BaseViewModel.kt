package ru.akbirov.weather.app.screens.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.akbirov.weather.R
import ru.akbirov.weather.app.utils.MutableLiveEvent
import ru.akbirov.weather.app.utils.publishEvent
import ru.akbirov.weather.app.utils.share

open class BaseViewModel: ViewModel() {

    private val _showErrorMessageEvent = MutableLiveEvent<String>()
    val showErrorMessageEvent = _showErrorMessageEvent.share()

    private val _showSuccessMessageEvent = MutableLiveEvent<String>()
    val showSuccessMessageEvent = _showSuccessMessageEvent.share()

    fun CoroutineScope.safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                _showErrorMessageEvent.publishEvent(e.message ?: "Error")
            }
        }
    }

    fun onSuccess(message: String?) {
        _showSuccessMessageEvent.publishEvent(message ?: "Done!")
    }
}