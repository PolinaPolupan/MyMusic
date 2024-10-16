package com.example.mymusic.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.junit.rules.TemporaryFolder
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataStoreModule::class],
)
internal object TestDataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        tmpFolder: TemporaryFolder,
    ): DataStore<Preferences> =
        tmpFolder.testUserPreferencesDataStore()
}

fun TemporaryFolder.testUserPreferencesDataStore() =
    PreferenceDataStoreFactory.create(
        produceFile = { newFile("user_preferences_test.pb") }
    )

