package com.example.mymusic.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences (
    val isSpotifyInstalled: Boolean
)

class UserDataRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val IS_SPOTIFY_INSTALLED = booleanPreferencesKey("is_Spotify_installed")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val isInstalled = preferences[PreferencesKeys.IS_SPOTIFY_INSTALLED]?: false
            UserPreferences(isInstalled)
        }

    suspend fun updateIsSpotifyInstalled(isInstalled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_SPOTIFY_INSTALLED] = isInstalled
        }
    }
}
