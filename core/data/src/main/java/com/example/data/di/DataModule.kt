package com.example.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.common.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsMusicRepository(
        musicRepository: com.example.data.repository.OfflineFirstMusicRepository,
    ): com.example.data.repository.MusicRepository

    @Binds
    internal abstract fun bindsUserDataRepository(
        userDataRepository: com.example.data.repository.OfflineFirstUserDataRepository,
    ): com.example.data.repository.UserDataRepository
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