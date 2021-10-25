package com.tugcearas.weatherapp.backend

import com.tugcearas.weatherapp.BuildConfig
import com.tugcearas.weatherapp.backend.data.MWLocation
import com.tugcearas.weatherapp.backend.data.MWLocationInfo
import com.tugcearas.weatherapp.backend.data.MWWeather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaWeatherService {

    companion object {

        // singleton instance
        val instance: MetaWeatherService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DOMAIN + "api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(MetaWeatherService::class.java)
        }

        fun getImageUrl(abbr: String): String {
            return BuildConfig.DOMAIN + "static/img/weather/png/" + abbr + ".png"
        }
    }

    @GET("location/search")
    fun searchLocation(@Query("query") txt: String): Call<List<MWLocation>>

    @GET("location/{woeid}")
    fun getLocationInfo(@Path("woeid") woeid: Int): Call<MWLocationInfo>

    @GET("location/{woeid}/{yyyy}/{mm}/{dd}")
    fun getWeatherForDay(
        @Path("woeid") woeid: Int,
        @Path("yyyy") year: Int,
        @Path("mm") month: Int,
        @Path("dd") day: Int
    ): Call<List<MWWeather>>
}