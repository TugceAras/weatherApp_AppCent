package com.tugcearas.weatherapp.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MWRetrofitBuilder {

    private val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl("https://www.metaweather.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService():MetaWeatherService{
        return retrofit.create(MetaWeatherService::class.java)
    }
}