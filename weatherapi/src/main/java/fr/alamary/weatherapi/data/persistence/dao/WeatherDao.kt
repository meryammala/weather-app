package fr.alamary.weatherapi.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber

@Dao
interface WeatherDao {

    @Query("Select * from weathers where cityId = :mCityId")
    fun getWeatherByCity(mCityId :Int): Observable<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeathers(cityEntity: CityEntity)

    @Query("DELETE FROM weathers")
    fun clear()
}

