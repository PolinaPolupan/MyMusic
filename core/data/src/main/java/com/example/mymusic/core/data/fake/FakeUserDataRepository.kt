package com.example.mymusic.core.data.fake

import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.datastore.MyMusicPreferencesDataSource
import com.example.mymusic.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeUserDataRepository @Inject constructor(
    private val dataSource: MyMusicPreferencesDataSource
): UserDataRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = dataSource.userPreferencesFlow.map {
        UserPreferences(
            authState = if (it.authState.isNullOrEmpty()) "test" else it.authState, // Initialize auth state with non-empty value to prevent authorization during testing
            displayName = it.displayName,
            email = it.email,
            imageUrl = it.imageUrl,
            trackId = it.trackId,
            isPlaying = it.isPlaying,
            spotifyUri = it.spotifyUri
        )
    }

    override suspend fun updateAuthState(authState: String) = dataSource.updateAuthState(authState)

    override suspend fun updateUserData(displayName: String, email: String, imageUrl: String) = dataSource.updateUserData(displayName, email, imageUrl)

    override suspend fun setIsPlaying(isPlaying: Boolean) = dataSource.setIsPlaying(isPlaying)

    override suspend fun setTrackId(trackId: String) = dataSource.setTrackId(trackId)

    override suspend fun setSpotifyUri(spotifyUri: String) = dataSource.setSpotifyUri(spotifyUri)
}