package fr.alamary.weatherapi.data.source.remote.globals

import fr.alamary.weatherapi.data.source.remote.services.IWeatherService

object ApiClientServiceProvider {

    fun buildWeatherService() : IWeatherService {
        return WeatherApiClient.getClient(ApiConfig().baseURL)!!.create(IWeatherService::class.java)
    }
}