package fr.alamary.weatherapi.domain.usecases

import fr.alamary.weatherapi.data.source.remote.exceptions.RxErrorHandler
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.domain.repositories.IWeatherRepository
import fr.alamary.weatherapi.domain.entities.CityEntity
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue


class WeatherUseCase(private val IWeatherRepository: IWeatherRepository) {

    fun getGlobalWeatherInformations(city : CityEntity, lang : String? = "EN", subscriber: Subscriber<GlobalWeatherInformations>){
        var tasks = LinkedBlockingQueue<Runnable>()
        IWeatherRepository.getCityWeather(city,subscriber)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
            .onErrorResumeNext(RxErrorHandler<GlobalWeatherInformations>())
        tasks.take().run()
    }
   fun checkCity(cityName :String, subscriber: Subscriber<CityEntity>){
       var tasks = LinkedBlockingQueue<Runnable>()
       IWeatherRepository.checkCity(cityName,subscriber)
           .subscribeOn(Schedulers.io())
           .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
           .onErrorResumeNext(RxErrorHandler<CityEntity>())
       tasks.take().run()
   }
}