package fr.alamary.weatherapi.data.persistence.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import fr.alamary.weatherapi.domain.entities.ForecastEntity
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<ForecastEntity?>? {
        val listType: Type = object : TypeToken<List<ForecastEntity?>?>() {}.getType()
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<ForecastEntity?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}