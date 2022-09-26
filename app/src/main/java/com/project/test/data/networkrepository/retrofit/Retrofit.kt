package com.project.test.data.networkrepository.retrofit

import com.project.test.data.networkrepository.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RetrofitClient object that creates the [Retrofit] instance
 */
object RetrofitClient {
    /**
     * Base URL in which all our network requests will be based on
     */
    private val BASE_URL = "https://www.flickr.com/"

    /**
     * Retrofit Builder. Client is commented because it is used for the purpose of debugging
     * calls in this case.
     */
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        //.client(createClient())
        .build()
    val retroclient = retrofit.create(ApiService::class.java)

    /**
     * Builder to add interceptor to Retrofit calls so can check the response.
     */
    private fun createClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val response = chain.proceed(chain.request())
                response
            })
            .build()
        return client
    }
}