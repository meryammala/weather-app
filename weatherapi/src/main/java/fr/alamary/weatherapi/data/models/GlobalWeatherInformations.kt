package fr.alamary.weatherapi.data.models

import com.google.gson.annotations.SerializedName

data class GlobalWeatherInformations(
    @SerializedName("lat") val lat : Double? = null,
    @SerializedName("lon") val lon : Double? = null,
    @SerializedName("timezone") val timezone : String? = null,
    @SerializedName("timezone_offset") val timezoneOffset : Int? = null,
    @SerializedName("current") val current : Current? = null,
    @SerializedName("minutely") val minutely : List<Minutely>? = null,
    @SerializedName("hourly") val hourly : List<Hourly>? = null,
    @SerializedName("daily") val daily : List<Daily>? = null
)
