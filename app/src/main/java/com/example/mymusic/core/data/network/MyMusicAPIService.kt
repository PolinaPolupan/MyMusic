package com.example.mymusic.core.data.network

import retrofit2.http.GET

interface MyMusicAPIService {

    @GET("https://api.spotify.com/v1/recommendations?limit=10&seed_genres=classical%2Ccountry")
    suspend fun getRecommendations(): List<SpotifyTrack>
}
