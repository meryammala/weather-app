package fr.alamary.weatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import fr.alamary.weatherapi.domain.usecases.WeatherUseCase
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class WeatherViewModel  : ViewModel(){

    val getWeatherSuccessLiveData = MutableLiveData<WeatherEntity>()
    val getWeatherFailureLiveData = MutableLiveData<Throwable>()
    private val usecase = WeatherUseCase()

    //fetch weather using weatherUsecase
    fun getWeather(cityEntity: CityEntity){
        usecase.getGlobalWeatherInformations(cityEntity,GerWeatherSubscriber())
    }

    //Subscriber to fetched cities
    open inner class GerWeatherSubscriber() : Subscriber<WeatherEntity> {

        override fun onComplete() = Unit

        override fun onSubscribe(s: Subscription?) = Unit

        override fun onNext(weather: WeatherEntity) {
            getWeatherSuccessLiveData.postValue(weather)
        }

        override fun onError(exception: Throwable?) {
            getWeatherFailureLiveData.postValue(exception)
        }
    }
}