package com.tugcearas.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugcearas.weatherapp.backend.MetaWeatherService
import com.tugcearas.weatherapp.backend.data.MWWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class PastLocationInfoViewModel : ViewModel() {

    private var woeid: Int = 0
    val cal: Calendar = Calendar.getInstance()
    private var callPastLocationInfo: Call<List<MWWeather>>? = null
    private val pastLocationInfo = MutableLiveData<List<MWWeather>>()
    private val progressBar = MutableLiveData<Boolean>()

    fun getProgressBar(): LiveData<Boolean> {
        return progressBar
    }

    fun getPastLocationInfo(): LiveData<List<MWWeather>> {
        return pastLocationInfo
    }

    fun getPreviousDayWeather(woeid: Int, applicable_date: String) {
        this.woeid = woeid
        cal.time = SimpleDateFormat("yyyy-MM-dd").parse(applicable_date)
        cal.add(Calendar.DATE, -1)
        getLocationDetails(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DATE)
        )
    }

    private fun getLocationDetails(year: Int, month: Int, date: Int) {
        // cancel previous request and continue with latest search text
        if (callPastLocationInfo != null) {
            callPastLocationInfo!!.cancel()
        }

        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, date)
        progressBar.postValue(true)
        callPastLocationInfo =
            MetaWeatherService.instance.getWeatherForDay(woeid, year, month + 1, date)
        callPastLocationInfo?.enqueue(object : Callback<List<MWWeather>> {
            override fun onFailure(call: Call<List<MWWeather>>, t: Throwable) {
                progressBar.postValue(false)
                when {
                    call.isCanceled -> {
                        Log.i(TAG, "request cancelled")
                    }
                    t is IOException -> {
                        Log.i(TAG, "No internet")
                    }
                    else -> {
                        Log.i(TAG, "unknown error")
                    }
                }
            }

            override fun onResponse(
                call: Call<List<MWWeather>>,
                response: Response<List<MWWeather>>
            ) {
                progressBar.postValue(false)
                pastLocationInfo.postValue(response.body())
            }
        })
    }

    fun selectedDate(year: Int, month: Int, day: Int) {
        getLocationDetails(year, month, day)
    }

    companion object {
        val TAG = PastLocationInfoViewModel::class.java.simpleName
    }

}