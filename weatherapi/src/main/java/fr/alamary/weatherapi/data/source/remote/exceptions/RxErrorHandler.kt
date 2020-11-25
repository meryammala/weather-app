package fr.alamary.weatherapi.data.source.remote.exceptions

import io.reactivex.functions.Function
import io.reactivex.ObservableSource

import retrofit2.HttpException
import retrofit2.Response

/**
 * Retrofit Error Handler to catch error with RX Observable
 */

class RxErrorHandler<T> : Function<Throwable, ObservableSource<T>> {

    /***
     * switch on the throwable instance Type to differentiate between HttpExceptions and api custom exception
     */
    @Throws
    override fun apply(throwable: Throwable): ObservableSource<T> {
        if (throwable is HttpException) {
            val httpException = throwable
            if (httpException.response()!!.isSuccessful()) {
                error(ServiceException("Parsing error occurred"))
            } else {
                error(ApiException(httpException.response() as Response<Any>))
            }
        }
        return error(ServiceException("Connection Error - " + throwable.message))    }
}