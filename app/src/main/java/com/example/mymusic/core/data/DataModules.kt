package com.example.mymusic.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.TokenInterceptor
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AuthorizationManagerModule {
    @Provides
    @Singleton
    fun provideAuthorizationManager(
        @ApplicationContext context: Context,
        userDataRepository: UserDataRepository
    ) : AuthorizationManager {
        return AuthorizationManager(userDataRepository, context)
    }
}

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.DATASTORE_PREFERENCES_NAME) }
        )
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun provideAPIService(
        authorizationManager: AuthorizationManager
    ): MyMusicAPIService  {
        val client = OkHttpClient.Builder()
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