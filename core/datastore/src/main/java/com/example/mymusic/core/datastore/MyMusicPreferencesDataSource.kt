package com.example.mymusic.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mymusic.core.common.Constants
import com.example.mymusic.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class MyMusicPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val AUTH_STATE = stringPreferencesKey(Constants.AUTH_STATE)
        val DISPLAY_NAME = stringPreferencesKey(Constants.DATA_DISPLAY_NAME)
        val EMAIL = stringPreferencesKey(Constants.DATA_EMAIL)
        val IMAGE_URL = stringPreferencesKey(Constants.IMAGE_URL)
        val TRACK_ID = stringPreferencesKey(Constants.TRACK_ID) // Denotes the recent playable object
        val IS_PLAYING = booleanPreferencesKey(Constants.IS_PLAYING)
        val SPOTIFY_URI = stringPreferencesKey(Constants.SPOTIFY_URI)
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
            UserPreferences(
                authState = preferences[PreferencesKeys.AUTH_STATE],
                email = preferences[PreferencesKeys.EMAIL],
                displayName = preferences[PreferencesKeys.DISPLAY_NAME],
                imageUrl = preferences[PreferencesKeys.IMAGE_URL],
                trackId = preferences[PreferencesKeys.TRACK_ID],
                isPlaying = preferences[PreferencesKeys.IS_PLAYING],
                spotifyUri = preferences[PreferencesKeys.SPOTIFY_URI]
            )
        }

    suspend fun updateAuthState(authState: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_STATE] = authState
        }
    }

    suspend fun updateUserData(displayName: String, email: String, imageUrl: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_NAME] = displayName
            preferences[PreferencesKeys.EMAIL] = email
            preferences[PreferencesKeys.IMAGE_URL] = imageUrl
        }
    }

    suspend fun setIsPlaying(isPlaying: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_PLAYING] = isPlaying
        }
    }

    suspend fun setTrackId(trackId: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TRACK_ID] = trackId
        }
    }

    suspend fun setSpotifyUri(spotifyUri: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SPOTIFY_URI] = spotifyUri
        }
    }
}