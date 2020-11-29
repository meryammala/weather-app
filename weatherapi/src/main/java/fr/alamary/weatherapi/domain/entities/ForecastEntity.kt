package fr.alamary.weatherapi.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "forecasts",
    foreignKeys = [ForeignKey(
        entity = WeatherEntity::class,
        parentColumns = arrayOf("idWeather"),
        childColumns = arrayOf("idWeather"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "idWeather")
    var idWeather: Int = 0,

    @SerializedName("dt")
    val dt: Int,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double? = 0.0,
    @SerializedName("temp_max")
    val tempMax: Double? = 0.0,
    @SerializedName("icon")
    val icon: String
)