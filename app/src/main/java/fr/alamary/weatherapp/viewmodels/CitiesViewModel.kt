package fr.alamary.weatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.usecases.WeatherUseCase
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class CitiesViewModel : BaseViewModel() {

    private val weatherUseCase = WeatherUseCase()

    val getCitiesLiveData = MutableLiveData<List<CityEntity>>()
    val addCitySuccessLiveData = MutableLiveData<CityEntity>()
    val addCityFailureLiveData = MutableLiveData<Throwable>()

    //usecase getCities
    fun getCities() {
        weatherUseCase.getCities(GetCitiesSubscriber())
    }

    //usecase addCitie
    fun addCity(cityName : String) {
      weatherUseCase.checkAndAddCity(cityName,AddCitySubscriber())
    }


    //Subscriber to fetched added city of error message invalid city
    open inner class AddCitySubscriber : Subscriber<CityEntity> {

        override fun onComplete() = Unit

        override fun onSubscribe(s: Subscription?) = Unit

        override fun onNext(city: CityEntity) {
            addCitySuccessLiveData.postValue(city)
        }

        override fun onError(exception: Throwable?) {
            addCityFailureLiveData.postValue(exception)
        }
    }


    //Subscriber to fetched cities
    open inner class GetCitiesSubscriber() : Subscriber<List<CityEntity>> {

        override fun onComplete() = Unit

        override fun onSubscribe(s: Subscription?) = Unit

        override fun onNext(list: List<CityEntity>) {
            getCitiesLiveData.postValue(list)
        }

        override fun onError(exception: Throwable?) {

        }
    }


}