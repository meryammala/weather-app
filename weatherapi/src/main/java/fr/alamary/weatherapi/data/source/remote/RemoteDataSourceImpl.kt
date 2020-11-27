package fr.alamary.weatherapi.data.source.remote

import fr.alamary.weatherapi.data.mappers.CityMapper
import fr.alamary.weatherapi.data.mappers.WeatherMapper
import fr.alamary.weatherapi.data.models.GetWeatherByCityNameResponse
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.data.persistence.dao.CityDao
import fr.alamary.weatherapi.data.persistence.database.AppDatabase
import fr.alamary.weatherapi.data.source.local.LocalDataSourceImpl
import fr.alamary.weatherapi.data.source.remote.exceptions.ServiceException
import fr.alamary.weatherapi.data.source.remote.globals.AbstractServiceCallback
import fr.alamary.weatherapi.data.source.remote.globals.ApiClientServiceProvider
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig
import fr.alamary.weatherapi.data.source.remote.globals.BaseApiResult
import fr.alamary.weatherapi.data.source.remote.services.IWeatherService
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber

class RemoteDataSourceImpl : IRemoteDataSource {
    private val weatherApiService : IWeatherService = ApiClientServiceProvider.buildWeatherService()

    override fun saveCity(cityName: String,subscriber :Subscriber<CityEntity> ) : Observable<CityEntity>{
        val call = weatherApiService.getWeatherByCityName(cityName, ApiConfig().apiKey)
        return Observable.just(call.enqueue(object :
            AbstractServiceCallback<GetWeatherByCityNameResponse>() {
            override fun handleError(exception: ServiceException) {
                subscriber.onError(exception)
            }

            override fun handleSuccess(serviceResult: BaseApiResult<GetWeatherByCityNameResponse>) {
                val cityEntity = CityMapper().mapToEntity(serviceResult.data)
                //Save the city in database
                LocalDataSourceImpl().saveCity(cityEntity)
                subscriber.onNext(cityEntity)
            }

        })) as Observable<CityEntity>    }

    override fun getRemoteWeather(city: CityEntity,subscriber: Subscriber<WeatherEntity>): Observable<WeatherEntity> {
        val call = weatherApiService.getWeatherOneCall(
            lat = city.lat,
            lon = city.lon,
            apiKey = ApiConfig().apiKey
        )
        return Observable.just(call.enqueue(object :
            AbstractServiceCallback<GlobalWeatherInformations>() {
            override fun handleError(exception: ServiceException) {
                subscriber.onError(exception)
            }

            override fun handleSuccess(serviceResult: BaseApiResult<GlobalWeatherInformations>) {
                subscriber.onNext(WeatherMapper().mapToEntity(serviceResult.data))
            }


        })) as Observable<WeatherEntity>    }



}