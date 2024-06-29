package com.example.mymusic.core.data.network

import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.RecommendationsResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET

interface MyMusicAPIService {

    @GET("https://api.spotify.com/v1/recommendations?limit=50&seed_genres=pop")
    suspend fun getRecommendations(): NetworkResponse<RecommendationsResponse, ErrorResponse>
}
