package com.example.mymusic.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.mymusic.core.data.sync.SyncManager
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.Constants
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.TokenInterceptor
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.data.repository.OfflineFirstMusicRepository
import com.example.mymusic.core.data.repository.OfflineFirstUserDataRepository
import com.example.mymusic.core.data.repository.UserDataRepository
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsMusicRepository(
        musicRepository: OfflineFirstMusicRepository,
    ): MusicRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: OfflineFirstUserDataRepository,
    ): UserDataRepository
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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MusicDatabase::class.java,
            "music.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMusicDao(database: MusicDatabase): MusicDao = database.musicDao()
}

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {

    fun provideSyncManager(@ApplicationContext context: Context): SyncManager = SyncManager(context)
}