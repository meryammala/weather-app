package fr.alamary.weatherapi.domain.repositories

import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.domain.entities.CityEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber


interface IWeatherRepository {

    fun getCityWeather(city : CityEntity, subscriber: Subscriber<GlobalWeatherInformations>) : Observable<GlobalWeatherInformations>

    fun checkCity(cityName :String, subscriber: Subscriber<CityEntity>) : Observable<CityEntity>
}