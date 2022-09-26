package com.project.test.data.util

/**
 * Status that declares the state of each work being done
 */
enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * Wrapper that has the status of the work, data and message. We do this so we can observe that status.
 */
sealed class ApiResult<T>(val status: ApiStatus, val data: T?, val message: String?) {
    /**
     * Work is Loading (waiting response)
     */
    data class Loading<T>(val isLoading: Boolean) : ApiResult<T>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )

    /**
     * Work has completed successfully
     */
    data class Success<T>(val _data: T?) : ApiResult<T>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    /**
     * Work has failed.
     */
    data class Failure<T>(val errorMessage: String) : ApiResult<T>(
        status = ApiStatus.ERROR,
        data = null,
        message = errorMessage
    )
}