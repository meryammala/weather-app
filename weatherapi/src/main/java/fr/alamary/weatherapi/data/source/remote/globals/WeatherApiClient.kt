package fr.alamary.weatherapi.data.source.remote.globals

import android.util.Log
import com.google.gson.GsonBuilder
import fr.alamary.weatherapi.WeatherApiInitializer
import fr.alamary.weatherapi.data.source.remote.exceptions.NoConnectivityException
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig.Companion.CONTENT_TYPE_KEY
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig.Companion.CONTENT_TYPE_VALUE
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig.Companion.H_ACCEPT_JSON
import fr.alamary.weatherapi.data.source.remote.globals.ApiConfig.Companion.H_ACCEPT_KEY
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object WeatherApiClient {

    var retrofit: Retrofit? = null
    val gson =  GsonBuilder()
        .setLenient()
        .create()
    // Create and return an instance of Retrofit for a given @baseUrl param
    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl) //This is the onlt mandatory call on Builder object.
            .client(httpClient()) //The Htttp client to be used for requests
            .addConverterFactory(GsonConverterFactory.create(gson)) // Convertor library used to convert response into POJO
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        }
        return retrofit
    }
    fun getClientAuth(baseUrl: String): Retrofit? {
        //if (retrofit == null) {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl) //This is the onlt mandatory call on Builder object.
            .client(httpClient()) //The Htttp client to be used for requests
            .addConverterFactory(GsonConverterFactory.create(gson)) // Convertor library used to convert response into POJO
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        // }
        return retrofit
    }


    /**
     * Create Http client to be used to make Retrofit calls
     */
    private fun httpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(LogJsonInterceptor())
            .addInterceptor(ConnectivityInterceptor())
            .addNetworkInterceptor(NetworkInterceptor()) // Add Network interceptor
            .connectTimeout(60, TimeUnit.SECONDS) // 30 seconds Connection Timeout
            .readTimeout(60, TimeUnit.SECONDS) // 60 seconds Read Timeout
            .build()
    }
    class LogJsonInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val response = chain.proceed(request)
            val rawJson = response.body()!!.string()

            try {
                val jsonObject = JSONTokener(rawJson).nextValue()
                val jsonLog = if (jsonObject is JSONObject)
                    jsonObject.toString(4)
                else
                    (jsonObject).toString()
                Log.d("jsonLog", jsonLog)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // Re-create the response before returning it because body can be read only once
            return response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), rawJson)).build()
        }
    }
    // A custom Interceptor used as okHttp Network Interceptor
    class NetworkInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            // Build the request with defined headers
            var requestBuilder = originalRequest.newBuilder()
                .header(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE) // Override header content_type to application/json
                .header(H_ACCEPT_KEY, H_ACCEPT_JSON) // Override header accept to application/json
                .build()

            return chain.proceed(requestBuilder)

        }

    }
    class ConnectivityInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            if (!NetworkUtil.isOnline(WeatherApiInitializer.mContext)) {
                throw NoConnectivityException()
            }

            val builder: Request.Builder = chain.request().newBuilder()
            return chain.proceed(builder.build())

        }

    }
}