package fr.alamary.weatherapi.domain.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import fr.alamary.weatherapi.data.models.*

@Entity(
    tableName = "weathers",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = arrayOf("cityId"),
        childColumns = arrayOf("cityId"),
        onDelete = CASCADE
    )]
)
data class WeatherEntity(
    @ColumnInfo(name = "dt", index = true)
    var dt: Int,

    @SerializedName("sunrise")
    var sunrise: Int,
    @SerializedName("sunset")
    var sunset: Int,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("pressure")
    var pressure: Int,
    @SerializedName("humidity")
    var humidity: Int,
    @SerializedName("dew_point")
    var dewPoint: Double,
    @SerializedName("uvi")
    var uvi: Double,
    @SerializedName("clouds")
    var clouds: Int,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("wind_speed")
    var windSpeed: Double,
    @SerializedName("wind_deg")
    var windDeg: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("main")
    var main: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("icon")
    var icon: String,
    var hourly: List<ForecastEntity>,
    var daily: List<ForecastEntity>
){
    @PrimaryKey(autoGenerate = true)
    var idWeather : Int =0
    @ColumnInfo(name = "cityId", index = true)
    var cityId: Int = 0
}


