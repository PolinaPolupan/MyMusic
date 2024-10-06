package com.example.mymusic.core.network.di

import com.example.mymusic.core.auth.AuthorizationManager
import com.example.mymusic.core.network.MyMusicAPIService
import com.example.mymusic.core.network.TokenInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideAPIService(
        authorizationManager: AuthorizationManager
    ): MyMusicAPIService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(TokenInterceptor(authorizationManager))
            .build()
        return Retrofit.Builder()
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://api.spotify.com")
            .build()
            .create(MyMusicAPIService::class.java)
    }
}