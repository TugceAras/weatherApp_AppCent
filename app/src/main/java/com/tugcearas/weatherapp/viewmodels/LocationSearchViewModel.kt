package com.tugcearas.weatherapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugcearas.weatherapp.backend.MetaWeatherService
import com.tugcearas.weatherapp.backend.data.MWLocation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LocationSearchViewModel : ViewModel() {

    private var callSearchLocations: Call<List<MWLocation>>? = null
    private val locations = MutableLiveData<List<MWLocation>>()
    private val progressBar = MutableLiveData<Boolean>()
    private var searchString:String? = null

    fun getProgressBar(): LiveData<Boolean> {
        return progressBar
    }

    fun getLocations(): LiveData<List<MWLocation>> {
        return locations
    }

    fun search(txt: String) {
        // cancel previous request and continue with latest search text
        if (callSearchLocations != null) {
            callSearchLocations!!.cancel()
        }

        if(searchString != null && searchString == txt){
           return
        }
        progressBar.postValue(true)
        callSearchLocations = MetaWeatherService.instance.searchLocation(txt)
        callSearchLocations?.enqueue(object : Callback<List<MWLocation>> {
            override fun onFailure(call: Call<List<MWLocation>>, t: Throwable) {
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
                call: Call<List<MWLocation>>,
                response: Response<List<MWLocation>>
            ) {
                progressBar.postValue(false)
                locations.postValue(response.body())
                searchString = txt
            }
        })
    }

    companion object {
        val TAG = LocationSearchViewModel::class.java.simpleName
    }

}