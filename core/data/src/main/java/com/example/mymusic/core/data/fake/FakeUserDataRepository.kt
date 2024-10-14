package com.example.mymusic.core.data.fake

import com.example.mymusic.core.data.repository.UserDataRepository
import com.example.mymusic.core.datastore.MyMusicPreferencesDataSource
import com.example.mymusic.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserDataRepository @Inject constructor(
    private val dataSource: MyMusicPreferencesDataSource
): UserDataRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = flow {
        emit(
            UserPreferences(
                authState = "test",
                displayName = "test",
                email = "test",
                imageUrl = "test"
            )
        )
    }
    override suspend fun updateAuthState(authState: String) = dataSource.updateAuthState(authState)

    override suspend fun updateUserData(displayName: String, email: String, imageUrl: String) = dataSource.updateUserData(displayName, email, imageUrl)
}