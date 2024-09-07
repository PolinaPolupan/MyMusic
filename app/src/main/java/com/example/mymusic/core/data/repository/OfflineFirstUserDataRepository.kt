package com.example.mymusic.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mymusic.core.data.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences (
    val authState: String?,
    val displayName: String?,
    val email: String?,
    val imageUrl: String?
)

class OfflineFirstUserDataRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val AUTH_STATE = stringPreferencesKey(Constants.AUTH_STATE)
        val DISPLAY_NAME = stringPreferencesKey(Constants.DATA_DISPLAY_NAME)
        val EMAIL = stringPreferencesKey(Constants.DATA_EMAIL)
        val IMAGE_URL = stringPreferencesKey(Constants.IMAGE_URL)
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
                imageUrl = preferences[PreferencesKeys.IMAGE_URL]
            )
        }

    suspend fun updateAuthState(authState: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_STATE] = authState
        }
    }

    /**
     * [updateUserData] updates user's display name and email
     */
    suspend fun updateUserData(displayName: String, email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_NAME] = displayName
            preferences[PreferencesKeys.EMAIL] = email
        }
    }

    /**
     * [updatePicture] updates user's picture. This function is separate from [updateUserData]
     * because images array from Spotify API can be empty
     * https://developer.spotify.com/documentation/web-api/reference/get-current-users-profile
     */
    suspend fun updateImageUrl(imageUrl: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IMAGE_URL] = imageUrl
        }
    }
}
