package fr.alamary.weatherapi

import android.content.Context
import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.data.source.remote.exceptions.ServiceException
import fr.alamary.weatherapi.data.source.remote.globals.AbstractServiceCallback
import fr.alamary.weatherapi.data.source.remote.globals.ApiClientServiceProvider
import fr.alamary.weatherapi.data.source.remote.globals.BaseApiResult
import fr.alamary.weatherapi.data.source.remote.services.IWeatherService
import fr.alamary.weatherapi.domain.CityEntity
import fr.alamary.weatherapi.domain.IWeatherRepository
import fr.alamary.weatherapi.domain.WeatherUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.reactivestreams.Subscriber
import retrofit2.Response

class WeatherApiServiceTest {
    @get:Rule
    public val mockitoRule = MockitoJUnit.rule()
    private lateinit var apiService : IWeatherService

    @Mock
    val context : Context = Mockito.mock(Context::class.java)

    val validApiKey = "bd7a292e6db70407ed32e2f9305cfe58"
    val invalidApiKey = "bd7a292e6db70407ed32e2f9305cfe58XXX"
    val invalidLatitdue : Double = 91.0     // valid  inteval [-90, 90]
    val invalideLongitude : Double = 181.0   // valid longitude inteval [-180,180]

    @Mock
    val useCase = Mockito.mock(WeatherUseCase::class.java)

    @Mock
    val manager = Mockito.mock(IWeatherRepository::class.java)

    @Mock
    var city = Mockito.mock(CityEntity::class.java)

    @Mock
    val mockedSubscriber : Subscriber<*> = Mockito.mock(Subscriber::class.java)

    @Mock
    val mockResponse : Response<*> = Mockito.mock(Response::class.java)

    @Before
    fun setup() {
        WeatherApiInitializer.initialize(validApiKey,context)
        apiService = ApiClientServiceProvider.buildWeatherService()
    }
    @Test
    fun getWeather_success(){

        var response = apiService.getWeatherOneCall(city.lat,city.lon,apiKey = validApiKey)!!.execute()
        assert(response.isSuccessful)
        assert(response.body()!=null)
        assert(response.body()!!.lat == city.lat)
        assert(response.body()!!.lon == city.lon)
    }
    @Test
    fun getWeather_Failure_Invalid_APIKEY(){
        val response =apiService.getWeatherOneCall(city.lat,city.lon,invalidApiKey)!!.execute()
        assert(!response.isSuccessful)
        assert(response.code()== 401)
        assert(response.message().equals("Unauthorized"))
    }

    @Test
    fun getWeather_Failure_Invalid_GPSCoordiante(){
        val response =apiService.getWeatherOneCall(city.lat,invalideLongitude,"${validApiKey}")!!.execute()
        assert(!response.isSuccessful)
        assert(response.code()== 400)
        assert(response.message().equals("Bad Request"))
    }
}