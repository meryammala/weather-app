package fr.alamary.weatherapi.domain

import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import io.reactivex.Observable
import org.reactivestreams.Subscriber


interface IWeatherRepository {

    fun getCityWeather(city :CityEntity, subscriber: Subscriber<GlobalWeatherInformations>) : Observable<GlobalWeatherInformations>
}