package fr.alamary.weatherapi.data.source.remote.globals

import retrofit2.Response

/**
 *  Use this class instead of retrofit Response<T> to customize Retrofit response
 *  @data is a generic type that references to Data incoming from api
 *  @response : to customize Response of Retrofit call
 */
class BaseApiResult <T>(val data: T, val response: Response<T>)