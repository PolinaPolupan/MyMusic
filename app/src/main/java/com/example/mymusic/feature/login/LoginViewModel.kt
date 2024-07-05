package com.example.mymusic.feature.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.mymusic.core.data.AuthorizationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager
): ViewModel() {

    fun signIn(): Intent {
        return authorizationManager.signIn()
    }

    fun handleAuthorizationResponse(intent: Intent) {
        authorizationManager.handleAuthorizationResponse(intent)
    }
}
