package fr.alamary.weatherapi.data.repositories

import fr.alamary.weatherapi.data.source.remote.exceptions.ServiceException
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.data.source.remote.globals.BaseApiResult
import fr.alamary.weatherapi.data.source.remote.globals.AbstractServiceCallback
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig
import fr.alamary.weatherapi.data.source.remote.services.IWeatherService
import fr.alamary.weatherapi.domain.CityEntity
import fr.alamary.weatherapi.domain.IWeatherRepository
import io.reactivex.Observable
import org.reactivestreams.Subscriber


class WeatherRepositoryImpl(private val weatherApiService : IWeatherService) : IWeatherRepository {


    override fun getCityWeather(city: CityEntity, subscriber: Subscriber<GlobalWeatherInformations>): Observable<GlobalWeatherInformations> {
        val call = weatherApiService.getWeatherOneCall(lat = city.lat!!,
            lon = city.lon!!,
            apiKey = ApiConfig().apiKey)
        return Observable.just(call.enqueue(object :
            AbstractServiceCallback<GlobalWeatherInformations>() {
            override fun handleError(exception: ServiceException) {
                TODO("Not yet implemented")
            }

            override fun handleSuccess(serviceResult: BaseApiResult<GlobalWeatherInformations>) {
                TODO("Not yet implemented")
            }


        })) as Observable<GlobalWeatherInformations>
    }


}