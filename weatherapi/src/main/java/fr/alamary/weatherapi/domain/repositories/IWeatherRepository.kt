package fr.alamary.weatherapi.domain.repositories

import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber


interface IWeatherRepository {

    // global getWeather method allow to fetch weather from data source (remote / local)
    fun getWeather(city : CityEntity,  subscriber: Subscriber<WeatherEntity>) : Observable<WeatherEntity>

    // Fetch remote weather using api
    fun getRemoteCityWeather(city : CityEntity, subscriber: Subscriber<WeatherEntity>) : Observable<WeatherEntity>

    //Fetch weather from database
    fun getLocalCityWeather(city : CityEntity, subscriber: Subscriber<WeatherEntity>) : Observable<WeatherEntity>

    // Before adding city to the database, this methods allows to check valid city and get city info by calling open weather api
    fun checkAndAddCity(cityName :String, subscriber: Subscriber<CityEntity>) : Observable<CityEntity>

    //fetch stored cities from database
    fun getLocalCities(subscriber: Subscriber<List<CityEntity>>):Observable<List<CityEntity>>
}