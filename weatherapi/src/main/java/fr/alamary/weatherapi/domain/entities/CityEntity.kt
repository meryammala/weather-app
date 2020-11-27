package fr.alamary.weatherapi.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "cities",
    indices =
    [Index(
        value = [ "name","lat","long" ],
        unique = true
    )]

)
data class CityEntity(
    @PrimaryKey(autoGenerate = true) var cityId: Int = 0,

    @ColumnInfo(name = "lat", index = true)
    @SerializedName("lat") val lat: Double,

    @ColumnInfo(name = "long", index = true)
    @SerializedName("long") val lon: Double,
    @ColumnInfo(name = "name", index = true)
    @SerializedName("name") var name: String? = "",
    @SerializedName("timezone") var timezone: String? = "",
    @SerializedName("timezoneOffset") var timezoneOffset: Int? = null,
    @SerializedName("country_code") val countryCode: String? = ""
)

