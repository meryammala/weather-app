package fr.alamary.weatherapi.data.source.local

import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber

interface ILocalDataSource {

     fun getSavedCities(subscriber: Subscriber<List<CityEntity>>) : Observable<List<CityEntity>>
     fun getSavedWeather(
        cityEntity: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity>
}