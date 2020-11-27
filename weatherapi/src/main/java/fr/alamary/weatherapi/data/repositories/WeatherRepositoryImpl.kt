package fr.alamary.weatherapi.data.repositories

import fr.alamary.weatherapi.data.mappers.CityMapper
import fr.alamary.weatherapi.data.models.GetWeatherByCityNameResponse
import fr.alamary.weatherapi.data.source.remote.exceptions.ServiceException
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.data.source.remote.globals.BaseApiResult
import fr.alamary.weatherapi.data.source.remote.globals.AbstractServiceCallback
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig
import fr.alamary.weatherapi.data.source.remote.services.IWeatherService
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.repositories.IWeatherRepository
import io.reactivex.Observable
import org.reactivestreams.Subscriber


class WeatherRepositoryImpl(private val weatherApiService: IWeatherService) : IWeatherRepository {


    override fun getCityWeather(
        city: CityEntity,
        subscriber: Subscriber<GlobalWeatherInformations>
    ): Observable<GlobalWeatherInformations> {
        val call = weatherApiService.getWeatherOneCall(
            lat = city.lat,
            lon = city.lon,
            apiKey = ApiConfig().apiKey
        )
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

    override fun checkCity(
        cityName: String,
        subscriber: Subscriber<CityEntity>
    ): Observable<CityEntity> {

        val call = weatherApiService.getWeatherByCityName(cityName,ApiConfig().apiKey)
        return Observable.just(call.enqueue(object : AbstractServiceCallback<GetWeatherByCityNameResponse>(){
            override fun handleError(exception: ServiceException) {
                subscriber.onError(exception)
            }

            override fun handleSuccess(serviceResult: BaseApiResult<GetWeatherByCityNameResponse>) {
                val cityEntity = CityMapper().mapToEntity(serviceResult.data)
                subscriber.onNext(cityEntity)
            }

        }))as Observable<CityEntity>
    }


}