package com.example.mymusic

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.MusicRepository
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager,
    userDataRepository: UserDataRepository,
    private val musicRepository: MusicRepository
): ViewModel() {

    private val authState: Flow<String?> =
        userDataRepository.userPreferencesFlow
            .filterNotNull()
            .map {
                it.authState
            }

    suspend fun restoreState() {
        Log.i("MainActivity", authState.toString())
        authorizationManager.restoreState(authState.first())

    }

    suspend fun refresh() {
        musicRepository.refresh()
    }
}