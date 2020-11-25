package fr.alamary.weatherapi.data.source.remote.services

import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {

    /***
     * Fetch the current and forecast weather data of a given location
     * @param lat (REQUIRED)    : latitude (geographical coordinate)
     * @param long  (REQUIRED)  : longitude (geographical coordinate)
     * @param appid (REQUIRED)  :  ApiKey from the account openweathermap api
     *                            see https://openweathermap.org/
     * @param exclude (optional): To exclude some parts of the weather data from the API response.
     *                             It should be a comma-delimited list (without spaces).
     *                             Available values: current
     *                                               minutely
     *                                               hourly
     *                                               daily
     *                                               alerts
     * @param units (Optional)  : Units of measurement.
     *                           Available values: standard
     *                                             metric
     *                                             imperial
     *                           Default value :standard
     * @param lang (Optional)  : language of output
     *
     * For more informations https://openweathermap.org/api/one-call-api#data
     */
    @GET("data/2.5/onecall")
    fun getWeatherOneCall(
        @Query("lat")lat : Double,
        @Query("lon")lon : Double,
        @Query("appid")apiKey : String,
        @Query("exclude") excludes :String? ="",
        @Query("units") units :String? ="",
        @Query("lang") lang :String? =""
    ) : Call<GlobalWeatherInformations>
}