package fr.alamary.weatherapp.globals

import android.app.Application
import android.content.Context
import fr.alamary.weatherapi.WeatherApiInitializer
import fr.alamary.weatherapp.R

class BaseApplication : Application() {


    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()
        initializeWeatherApi()
    }

    /**
     * initialize the weather api with the client api key and the app context
     */
    private fun initializeWeatherApi() {
        WeatherApiInitializer.initialize(getString(R.string.open_weather_api_key), applicationContext)
    }


    companion object {
        private var instance: BaseApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}