package fr.alamary.weatherapi.data.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.alamary.weatherapi.domain.entities.CityEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber

@Dao
interface CityDao {

    @Query("Select * from cities")
    fun getAllCities(): Observable<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCity(city: CityEntity)

    @Query("DELETE FROM cities")
    fun clear()
}

