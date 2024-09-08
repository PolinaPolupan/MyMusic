package com.example.data.repository

import com.example.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    /**
     * Stream of [UserPreferences]
     */
    val userPreferencesFlow: Flow<UserPreferences>

    /**
     * Sets the user's current auth state
     */
    suspend fun updateAuthState(authState: String)

    /**
     * Updates user's display name, email, and image url
     */
    suspend fun updateUserData(displayName: String, email: String, imageUrl: String)
}