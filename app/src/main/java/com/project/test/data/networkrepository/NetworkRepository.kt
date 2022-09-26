package com.project.test.data.networkrepository

import com.project.test.data.networkrepository.retrofit.RetrofitClient

/**
 * NetworkRepository houses the instance of the [RetrofitClient]
 */
class NetworkRepository {
    /**
     * [RetrofitClient] instance
     */
    val apiService = RetrofitClient.retroclient

    companion object {
        fun create(): NetworkRepository {
            return NetworkRepository()
        }
    }
}