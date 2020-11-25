package fr.alamary.weatherapi.data.source.remote.exceptions

import com.google.gson.annotations.SerializedName
import fr.alamary.weatherapi.R
import fr.alamary.weatherapi.WeatherApiInitializer

/**
 * Data model error object to be created from parsing custom api errors
 * @code : error code from api
 * @message: error message from api
 */
data class ModelError(
    @SerializedName("cod") var code: Int? = 0,
    @SerializedName("message") var message: String) {



    fun getUnkownError(): ModelError {
        this.message = WeatherApiInitializer.mContext.resources.getString(R.string.unknown_exception_message)
        this.code = 500
        return this
    }

}