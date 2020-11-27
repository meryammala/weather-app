package fr.alamary.weatherapi.data.models

import com.google.gson.annotations.SerializedName

data class Cloud(@SerializedName("all") val all : Int) {
}