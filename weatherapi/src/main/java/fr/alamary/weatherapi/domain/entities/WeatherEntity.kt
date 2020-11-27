package fr.alamary.weatherapi.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import fr.alamary.weatherapi.data.models.*

@Entity(tableName = "weathers",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = arrayOf("cityId"),
        childColumns = arrayOf("cityId"),
        onDelete = CASCADE
    )]
)
class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    var idWeather: Int = 0,

    @ColumnInfo(name = "cityId", index = true)
    var cityId: Int = 0,

    @ColumnInfo(name = "dt", index = true)
    var dt: Int,

    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String)
