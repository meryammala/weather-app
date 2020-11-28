package fr.alamary.weatherapi.data.source.local

import fr.alamary.weatherapi.WeatherApiInitializer
import fr.alamary.weatherapi.data.persistence.dao.CityDao
import fr.alamary.weatherapi.data.persistence.dao.WeatherDao
import fr.alamary.weatherapi.data.persistence.database.AppDatabase
import fr.alamary.weatherapi.data.persistence.database.DatabaseBuilder
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber

class LocalDataSourceImpl() : ILocalDataSource {

    private val database: AppDatabase = DatabaseBuilder.getInstance(WeatherApiInitializer.mContext)



private val cityDao : CityDao = database.getCities()
    private val weatherDao : WeatherDao = database.getWeather()

    override fun getSavedCities(subscriber: Subscriber<List<CityEntity>>): Observable<List<CityEntity>> {
        return Observable.just(subscriber.onNext(cityDao.getAllCities()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())as Observable<List<CityEntity>>

    }

    override fun getSavedWeather(
        cityEntity: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity> =
        Observable.just(subscriber.onNext(weatherDao.getWeatherByCity(cityEntity.cityId)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) as Observable<WeatherEntity>
    fun saveCity(cityEntity:CityEntity) {
        Observable.just(cityDao.saveCity(cityEntity))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }
}


