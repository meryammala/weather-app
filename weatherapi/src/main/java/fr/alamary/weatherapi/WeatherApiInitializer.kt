package fr.alamary.weatherapi

import android.content.Context

object WeatherApiInitializer  {

    lateinit var mContext : Context
    private set
    lateinit var mApiKey : String
    private set

    fun initialize(apiKey : String,context: Context){
        mApiKey = apiKey
        mContext = context
    }


}