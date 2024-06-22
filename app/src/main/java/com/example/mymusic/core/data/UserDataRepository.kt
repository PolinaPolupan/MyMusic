package com.example.mymusic.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.mymusic.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences (
    val authState: String?,
    val displayName: String?,
    val email: String?
)

class UserDataRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private object PreferencesKeys {
        val AUTH_STATE = stringPreferencesKey(Constants.AUTH_STATE)
        val DISPLAY_NAME = stringPreferencesKey(Constants.DATA_DISPLAY_NAME)
        val EMAIL = stringPreferencesKey(Constants.SCOPE_EMAIL)
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
                displayName = preferences[PreferencesKeys.DISPLAY_NAME]
            )
        }

    suspend fun updateAuthState(authState: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_STATE] = authState
        }
    }

    suspend fun updateUserData(displayName: String, email: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DISPLAY_NAME] = displayName
            preferences[PreferencesKeys.EMAIL] = email
        }
    }
}
