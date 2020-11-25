package fr.alamary.weatherapi.data.source.remote.globals

import fr.alamary.weatherapi.WeatherApiInitializer

class ApiConfig {

    var baseURL : String
    private set

    var apiKey : String
    private set


    companion object {
        const val END_POINT = "https://api.openweathermap.org/"
        const val CONTENT_TYPE_KEY = "Content-Type"
        const val H_ACCEPT_KEY = "Accept"
        const val CONTENT_TYPE_VALUE = "application/json"
        const val H_ACCEPT_JSON = "application/json"
    }
    init {
        baseURL = END_POINT
        apiKey = WeatherApiInitializer.mApiKey
    }
}