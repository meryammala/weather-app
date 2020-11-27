package fr.alamary.weatherapi.data.models

import com.google.gson.annotations.SerializedName

data class Coordinate(@SerializedName("lon") val lon : Double,
                      @SerializedName("lat") val lat : Double)