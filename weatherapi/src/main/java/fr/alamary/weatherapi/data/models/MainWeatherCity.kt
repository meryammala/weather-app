package fr.alamary.weatherapi.data.models

import com.google.gson.annotations.SerializedName

data class MainWeatherCity(@SerializedName("temp") val temp : Double,
                      @SerializedName("feels_like") val feels_like : Double,
                      @SerializedName("temp_min") val temp_min : Double,
                      @SerializedName("temp_max") val temp_max : Double,
                      @SerializedName("pressure") val pressure : Int,
                      @SerializedName("humidity") val humidity : Int) {
}