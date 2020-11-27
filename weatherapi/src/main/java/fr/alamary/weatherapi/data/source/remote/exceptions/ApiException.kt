package fr.alamary.weatherapi.data.source.remote.exceptions

import com.google.gson.Gson
import fr.alamary.weatherapi.data.source.remote.globals.WeatherApiClient
import retrofit2.Response
import java.io.IOException

/**
 * Custom Exception to handle custom Api Errors
 * @apiError: to handle custom error send from API
 * @apiErrorCode: refers to error code
 * @response: the response with error that we want to customize
 */
class ApiException : ServiceException {

    private lateinit var apiError: ModelError
    private var apiErrorCode: Int = 0
    private lateinit var response: Response<Any>

    /**
     * Create the default ApiException object
     */
    constructor(response: Response<Any>) : super("DEFAULT_ERROR_CODE") {
        ApiException(
            response = response,
            apiError = readApiError(response),
            apiErrorCode = 0
        )
    }

    /**
     * Create the default ApiException object with parameters
     * @apiError : refer to the api response with a custom errorBody
     * @apiErrorCode: refer to the error code
     * @repsonse : the response fetech from retrofit Call<T>
     */
    constructor(response: Response<Any>, apiError: ModelError, apiErrorCode: Int) : super("DEFAULT_ERROR_CODE") {
        this.apiError = apiError
        this.apiErrorCode = apiErrorCode
        this.response = response
    }

    /**
     * static method to read and convert the @errorBody to our custom error object @modelError
     */
    companion object {
        fun readApiError(response: Response<Any>): ModelError {
            return ErrorUtils.parseError(response)

        }

        fun parseApiError(body: String): ModelError {
            return Gson().fromJson<ModelError>(body, ModelError::class.java)
        }

    }
    }


