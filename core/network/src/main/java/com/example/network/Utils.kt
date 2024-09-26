package com.example.network

import android.util.Log
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * [processResponse] utility function. Handles and logs errors and returns the given data
 *
 * @param response parameter type of NetworkResponse. Contains successful and error response data
 * @param successData data which should be returned if the response is successful
 * @param errorData data which should be returned if the response has failed
 */
fun <S, E, T> processResponse(response: NetworkResponse<S, E>, successData: T, errorData: T): T {
    return when (response) {
        is  NetworkResponse.Success -> {
            Log.d("MainActivity", "Request is successful: ${response.body.toString()}")
            successData
        }

        is NetworkResponse.NetworkError -> {
            Log.e("MainActivity", "Request failed ${response.error.message ?: "Network Error"}")
            errorData
        }

        is NetworkResponse.ServerError -> {
            Log.e("MainActivity", "Request failed. Code: ${response.code.toString()} ${response.error?.message ?: "Server Error"}")
            errorData
        }

        is NetworkResponse.UnknownError -> {
            Log.e("MainActivity", "Request failed. Code: ${response.code.toString()} ${response.error.message ?: "Unknown Error"}")
            errorData
        }
    }
}