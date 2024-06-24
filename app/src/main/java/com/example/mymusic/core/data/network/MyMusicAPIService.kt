package com.example.mymusic.core.data.network

import android.util.Log
import com.example.mymusic.core.data.Track
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyMusicAPIService {

    @GET("https://api.spotify.com/v1/recommendations?limit=10&seed_genres=classical%2Ccountry")
    suspend fun getRecommendations(): List<Track>
}
