package fr.alamary.weatherapi.data.source.remote

import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber

interface IRemoteDataSource {

    fun saveCity(name: String, subscriber: Subscriber<CityEntity>) : Observable<CityEntity>
    fun getRemoteWeather(cityEntity: CityEntity,subscriber: Subscriber<WeatherEntity>) : Observable<WeatherEntity>
}