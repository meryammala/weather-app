package fr.alamary.weatherapi.domain

import fr.alamary.weatherapi.data.source.remote.exceptions.RxErrorHandler
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue


class WeatherUseCase(private val IWeatherRepository: IWeatherRepository) {

    fun getGlobalWeatherInformations(city : CityEntity,lang : String? = "EN",subscriber: Subscriber<GlobalWeatherInformations>){
        var tasks = LinkedBlockingQueue<Runnable>()
        IWeatherRepository.getCityWeather(city,subscriber)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.from(Executor { runnable -> tasks.add(runnable) }))
            .onErrorResumeNext(RxErrorHandler<GlobalWeatherInformations>())
        tasks.take().run()
    }
    //todo: add usecases to configure request with advanced parameters
}