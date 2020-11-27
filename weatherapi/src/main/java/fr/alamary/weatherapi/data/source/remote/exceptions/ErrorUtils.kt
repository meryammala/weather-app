package fr.alamary.weatherapi.data.source.remote.exceptions

import fr.alamary.weatherapi.data.source.remote.globals.WeatherApiClient
import retrofit2.Response
import java.io.IOException

object ErrorUtils {
    fun parseError(response: Response<Any>): ModelError {
        val converter = WeatherApiClient.retrofit?.responseBodyConverter<ModelError>(ModelError::class.java, arrayOf<Annotation>())

        val error: ModelError

        try {
            error = converter!!.convert(response.errorBody())!!
        } catch (e: IOException) {
            return ModelError(500,"Server error")
        }

        return error
    }
}