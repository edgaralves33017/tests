package com.project.test.data

import com.project.test.data.networkrepository.NetworkRepository
import com.project.test.data.util.ApiResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Repository class that houses instances to [NetworkRepository].
 * As per MVVM architecture, every access to [Repository] is transparent, and whoever accesses the methods
 * doesn't need to know if they are using [NetworkRepository] or another repository.
 */
class Repository(
    /**
     * Instance of NetworkRepository
     */
    private val networkService: NetworkRepository
) {
    /**
     * ApiService from NetworkingRepository
     */
    private val apiService
        get() = networkService.apiService

    /**
     * Method that does the request to get the Public Feed.
     * The flow has 3 states: [ApiResult.Loading] when the work isn't finished, [ApiResult.Success]
     * after getting a successful response and [ApiResult.Failure] when something goes wrong.
     * @return list of [Entry][com.project.test.data.models.Entry] objects
     */
    suspend fun getPublicFeed() = flow {
        emit(ApiResult.Loading(true))
        val response = apiService.getPublicFeed()
        emit(ApiResult.Success(response.body()?.items))
    }.catch { e ->
        emit(ApiResult.Failure(e.message ?: "Unknown Error"))
    }
}