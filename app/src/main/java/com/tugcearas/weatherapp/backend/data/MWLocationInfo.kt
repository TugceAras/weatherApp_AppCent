package com.tugcearas.weatherapp.backend.data

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

data class MWLocationInfo(
    val consolidated_weather: List<MWWeather>,
    val time: String,
    val sun_rise: String,
    val sun_set: String,
    val timezone_name: String,
    val parent: MWParent,
    val sources: List<MWSource>,
    val title: String,
    val location_type: String,
    val woeid: Int,
    val latt_long: String,
    val timezone: String
) {

    private fun format(str: String): String {

        try {
            val slits = str.split(".")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            inputFormat.timeZone = TimeZone.getTimeZone(timezone/*"Europe/London"*/)

            val outputFormat = SimpleDateFormat("h:mm a")
            outputFormat.timeZone = inputFormat.timeZone
            return outputFormat.format(inputFormat.parse(slits[0])!!)
        } catch (e: Exception) {
            Log.e(MWLocationInfo::class.java.simpleName, e.stackTrace.toString());
        }
        return "-"
    }

    fun formattedTime(): String {
        return format(time)
    }

    fun formattedSunriseTime(): String {
        return format(sun_rise)
    }

    fun formattedSunsetTime(): String {
        return format(sun_set)
    }
}