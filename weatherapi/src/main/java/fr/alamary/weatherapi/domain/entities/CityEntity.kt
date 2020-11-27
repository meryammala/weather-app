package fr.alamary.weatherapi.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("lat") val lat: Double,
    @SerializedName("long") val lon: Double,
    @SerializedName("name") var name: String? = "",
    @SerializedName("timezone") var timezone: String? = "",
    @SerializedName("timezoneOffset") var timezoneOffset: Int? = null,
    @SerializedName("country_code") val countryCode: String? = ""
)

