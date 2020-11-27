package fr.alamary.weatherapi.domain

import com.google.gson.annotations.SerializedName

data class CityEntity(@SerializedName("lat") val lat : Double?=null,
                      @SerializedName("long") val lon : Double?=null,
                      @SerializedName("name") var name : String? ="",
                      @SerializedName("timezone") var timezone : String?="")

