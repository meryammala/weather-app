package fr.alamary.weatherapi.data.source.remote.globals

import fr.alamary.weatherapi.data.source.remote.exceptions.ApiException
import fr.alamary.weatherapi.data.source.remote.exceptions.ServiceException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/***
 *  AbstractServiceCallback is a custom Retrofit Callback<T>
 */
abstract class AbstractServiceCallback<T> : Callback<T> {

    /**
     * Custom error handling method
     * @exception : Custom Exception that refers to Web Service Exception
     */
    abstract fun handleError(exception: ServiceException)

    /**
     * Custom success handling method
     * @serviceResult : Custom Api SearchTenantResult
     */
    abstract fun handleSuccess(serviceResult : BaseApiResult<T>)

    /**
     * Override Callback<T> method to use our @handleError method instead of onFailure Retrofit Call<T>
     */
    override fun onFailure(call: Call<T>, t: Throwable) {
        /**
         * handle error with custom Exception @ServiceException
         */
        handleError(ServiceException("Request Failure"))
    }

    /**
     * Override Callback<T> method to use our @handleSuccess method instead of onResponse Retrofit Call<T>
     * response can be successful with a no null @body
     * Or fail with a custom api error located on @errorBody
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        /**
         * handleSucces when response is successful
         * Or handleFailure when response is not successful
         */
        if(response.isSuccessful){
            handleSuccess(BaseApiResult<T>(data = response.body()!!,response = response))
        }else{
            handleError(ApiException(response = response as Response<Any>))
        }

    }

}