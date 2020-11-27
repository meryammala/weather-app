package fr.alamary.weatherapi.domain.usecases

import android.annotation.SuppressLint
import fr.alamary.weatherapi.data.source.remote.exceptions.RxErrorHandler
import fr.alamary.weatherapi.data.repositories.WeatherRepositoryImpl
import fr.alamary.weatherapi.domain.repositories.IWeatherRepository
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue


class WeatherUseCase() {
    private val weatherRepository: IWeatherRepository = WeatherRepositoryImpl()

    // Get global weather informations using one call weather api
    fun getGlobalWeatherInformations(city : CityEntity, subscriber: Subscriber<WeatherEntity>){
        var tasks = LinkedBlockingQueue<Runnable>()
        weatherRepository.getWeather(city,subscriber)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
            .onErrorResumeNext(RxErrorHandler<WeatherEntity>())
        tasks.take().run()
    }

    // add a city to the database after checking correct city name using open weather api (get weather by city name)
    // PS : in the case when this use case uses another webservice, the best way is to create a new use case class that
    //     will use a new repository for example AddCityUseCase class will use CityRepository
   fun checkAndAddCity(cityName :String, subscriber: Subscriber<CityEntity>){
       var tasks = LinkedBlockingQueue<Runnable>()
       weatherRepository.checkAndAddCity(cityName,subscriber)
           .subscribeOn(Schedulers.io())
           .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
           .onErrorResumeNext(RxErrorHandler<CityEntity>())
       tasks.take().run()
   }

    @SuppressLint("CheckResult")
    fun getCities(subscriber: Subscriber<List<CityEntity>>) {
        var tasks = LinkedBlockingQueue<Runnable>()
        weatherRepository.getLocalCities(subscriber)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
            .onErrorResumeNext(RxErrorHandler<List<CityEntity>>())
        tasks.take().run()
    }


}