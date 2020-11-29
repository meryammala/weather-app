package fr.alamary.weatherapi.data.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.alamary.weatherapi.data.persistence.dao.CityDao
import fr.alamary.weatherapi.data.persistence.dao.WeatherDao
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity

@Database(entities = arrayOf(CityEntity::class,WeatherEntity::class),exportSchema = false, version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWeather(): WeatherDao
    abstract fun getCities(): CityDao
}