package com.project.test.data.networkrepository.api


import com.project.test.data.models.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface that has the necessary endpoint for us to make HTTP/HTTPS calls.
 */
interface ApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun getPublicFeed(): Response<FlickrResponse>
}