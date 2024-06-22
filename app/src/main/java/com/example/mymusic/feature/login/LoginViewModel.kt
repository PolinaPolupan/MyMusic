package com.example.mymusic.feature.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.AuthorizationManager
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager,
    userDataRepository: UserDataRepository
): ViewModel() {

    private val _authState: StateFlow<String?> =
        userDataRepository.userPreferencesFlow
            .map {
                it.authState
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), null)

    init {
        authorizationManager.restoreState(_authState.value)
    }

    fun signIn(): Intent {
        return authorizationManager.signIn()
    }

    fun handleAuthorizationResponse(intent: Intent) {
        authorizationManager.handleAuthorizationResponse(intent, viewModelScope)
    }
}
