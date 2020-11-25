package fr.alamary.weatherapi.domain

import com.google.gson.annotations.SerializedName

data class CityEntity(@SerializedName("lat") val lat : Double,
                      @SerializedName("long") val lon : Double,
                      @SerializedName("name") var name : String? ="",
                      @SerializedName("timezone") var timezone : String?="",
                      @SerializedName("timezoneOffset") var timezoneOffset : Int?=null)

