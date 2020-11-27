package fr.alamary.weatherapi.data.repositories

import fr.alamary.weatherapi.data.source.local.LocalDataSourceImpl
import fr.alamary.weatherapi.data.source.remote.RemoteDataSourceImpl
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import fr.alamary.weatherapi.domain.repositories.IWeatherRepository
import io.reactivex.Observable
import org.reactivestreams.Subscriber


class WeatherRepositoryImpl : IWeatherRepository {

    private val cache = LocalDataSourceImpl()
    private val remote = RemoteDataSourceImpl()


    override fun getWeather(
        city: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity> {
        val updateNewsObservable = remote.getRemoteWeather(city, subscriber)
        return cache.getSavedWeather(city, subscriber).mergeWith {
            updateNewsObservable.doOnNext { cache.getSavedWeather(city, subscriber) }
        }
    }

    override fun getRemoteCityWeather(
        city: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity> {
        return remote.getRemoteWeather(city, subscriber)
    }

    override fun getLocalCityWeather(
        city: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity> {
        return cache.getSavedWeather(city, subscriber)
    }

    override fun checkAndAddCity(
        cityName: String,
        subscriber: Subscriber<CityEntity>
    ): Observable<CityEntity> {
        return remote.saveCity(cityName, subscriber)
    }

    override fun getLocalCities(subscriber: Subscriber<List<CityEntity>>): Observable<List<CityEntity>> {
        return cache.getSavedCities(subscriber)
    }


}