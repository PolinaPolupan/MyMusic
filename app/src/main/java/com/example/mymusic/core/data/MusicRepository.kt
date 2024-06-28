package com.example.mymusic.core.data

import android.util.Log
import com.example.mymusic.core.data.local.model.toExternal
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.toExternal
import com.example.mymusic.model.Track
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val apiService: MyMusicAPIService
) {

    suspend fun getRecommendations(): List<Track> {
        return when (val response = apiService.getRecommendations()) {
            is  NetworkResponse.Success -> {
                Log.d("MainActivity", response.body.toString())
                response.body.toExternal()
            }

            is NetworkResponse.NetworkError -> {
                Log.e("MainActivity", response.error.message ?: "Network Error")
                emptyList()
            }

            is NetworkResponse.ServerError -> {
                Log.e("MainActivity", response.error?.message ?: "Server Error")
                emptyList()
            }

            is NetworkResponse.UnknownError -> {
                Log.e("MainActivity", response.error.message ?: "Unknown Error")
                emptyList()
            }
        }
    }
}