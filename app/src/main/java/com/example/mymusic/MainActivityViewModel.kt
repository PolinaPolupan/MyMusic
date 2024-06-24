package com.example.mymusic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager,
    userDataRepository: UserDataRepository
): ViewModel() {

    val authState: Flow<String?> =
        userDataRepository.userPreferencesFlow
            .filterNotNull()
            .map {
                it.authState
            }

    init {
        viewModelScope.launch {
            Log.i("MainActivity", authState.toString())
            authorizationManager.restoreState(authState.first())
        }
    }
}