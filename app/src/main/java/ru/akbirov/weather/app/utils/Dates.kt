package ru.akbirov.weather.app.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDate(time: Int, format: String): String {
    val formatter = SimpleDateFormat(format)
    formatter.timeZone = TimeZone.getDefault()
    val date = Date(time.toLong() * 1000)

    return formatter.format(date)
}

const val DISPLAY_FORMAT = "E, dd MMM yyyy HH:mm"