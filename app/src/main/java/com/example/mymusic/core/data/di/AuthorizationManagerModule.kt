package com.example.mymusic.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.mymusic.Constants
import com.example.mymusic.core.AuthorizationManager
import com.example.mymusic.core.data.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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