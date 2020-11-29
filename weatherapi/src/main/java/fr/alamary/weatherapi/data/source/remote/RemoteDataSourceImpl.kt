package fr.alamary.weatherapi.data.source.remote

import fr.alamary.weatherapi.data.mappers.CityMapper
import fr.alamary.weatherapi.data.mappers.WeatherMapper
import fr.alamary.weatherapi.data.models.GetWeatherByCityNameResponse
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.data.source.local.LocalDataSourceImpl
import fr.alamary.weatherapi.data.source.remote.exceptions.ErrorUtils
import fr.alamary.weatherapi.data.source.remote.exceptions.NoConnectivityException
import fr.alamary.weatherapi.data.source.remote.globals.ApiClientServiceProvider
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig
import fr.alamary.weatherapi.data.source.remote.services.IWeatherService
import fr.alamary.weatherapi.domain.entities.CityEntity
import fr.alamary.weatherapi.domain.entities.WeatherEntity
import io.reactivex.Observable
import org.reactivestreams.Subscriber
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSourceImpl : IRemoteDataSource {
    private val weatherApiService: IWeatherService = ApiClientServiceProvider.buildWeatherService()
    private val localStore = LocalDataSourceImpl()

    override fun saveCity(
        cityName: String,
        subscriber: Subscriber<CityEntity>
    ): Observable<CityEntity> {
        val call = weatherApiService.getWeatherByCityName(cityName, ApiConfig().apiKey)
        return Observable.just(call.enqueue(object : Callback<GetWeatherByCityNameResponse> {
            override fun onResponse(
                call: Call<GetWeatherByCityNameResponse>,
                response: Response<GetWeatherByCityNameResponse>
            ) {
                when {
                    response.isSuccessful -> {
                        val cityEntity = CityMapper().mapToEntity(response.body()!!)
                        //Save the city in database
                        localStore.saveCity(cityEntity)
                        subscriber.onNext(cityEntity)
                    }
                    response.errorBody() != null -> {
                        val modelError = ErrorUtils.parseError(response as Response<Any>)
                        subscriber.onError(Exception(modelError.message))
                    }
                }
            }

            override fun onFailure(call: Call<GetWeatherByCityNameResponse>, t: Throwable) {
                subscriber.onError(t)
            }

        })) as Observable<CityEntity>
    }

    override fun getRemoteWeather(
        city: CityEntity,
        subscriber: Subscriber<WeatherEntity>
    ): Observable<WeatherEntity> {
        val call = weatherApiService.getWeatherOneCall(
            lat = city.lat,
            lon = city.lon,
            apiKey = ApiConfig().apiKey
        )
        return Observable.just(call.enqueue(object :
            Callback<GlobalWeatherInformations> {

            override fun onResponse(
                call: Call<GlobalWeatherInformations>,
                response: Response<GlobalWeatherInformations>
            ) {
                when {
                    response.isSuccessful -> {
                        val weatherEntity = WeatherMapper().mapToEntity(response.body()!!)
                        val storeCity = localStore.findCityByName(cityname = city.name!!)
                        weatherEntity.cityId = storeCity.cityId
                        localStore.saveWaether(weatherEntity)
                        subscriber.onNext(weatherEntity)
                    }
                    response.errorBody() != null -> {
                        val modelError = ErrorUtils.parseError(response as Response<Any>)
                        subscriber.onError(Exception(modelError.message))
                    }
                }
            }

            override fun onFailure(call: Call<GlobalWeatherInformations>, t: Throwable) {
                if(t is NoConnectivityException){
                    localStore.getSavedWeather(city,subscriber)
                }else{
                    subscriber.onError(t)
                }
            }


        })) as Observable<WeatherEntity>
    }


}