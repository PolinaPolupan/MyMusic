package com.example.mymusic.core.data.repository

import com.example.mymusic.core.datastore.MyMusicPreferencesDataSource
import com.example.mymusic.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class OfflineFirstUserDataRepository @Inject constructor(
    private val dataSource: MyMusicPreferencesDataSource
): UserDataRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = dataSource.userPreferencesFlow

    override suspend fun updateAuthState(authState: String) = dataSource.updateAuthState(authState)

    override suspend fun updateUserData(displayName: String, email: String, imageUrl: String) = dataSource.updateUserData(displayName, email, imageUrl)
}
