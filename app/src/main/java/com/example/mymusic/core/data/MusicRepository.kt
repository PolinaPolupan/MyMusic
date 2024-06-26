package com.example.mymusic.core.data

import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.model.Track
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val apiService: MyMusicAPIService
) {

    suspend fun getRecommendations(): List<Track>? {
        val response = apiService.getRecommendations()
        return response?.map { it.toExternal() }
    }
}