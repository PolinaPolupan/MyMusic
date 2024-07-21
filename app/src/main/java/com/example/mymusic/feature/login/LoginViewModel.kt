package com.example.mymusic.feature.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.sync.SyncManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager,
    private val syncManager: SyncManager
): ViewModel() {

    fun signIn(): Intent {
        return authorizationManager.signIn()
    }

    fun handleAuthorizationResponse(intent: Intent, onSuccess: () -> Unit) {
        authorizationManager.handleAuthorizationResponse(intent, onSuccess)
    }

    fun refresh() {
        viewModelScope.launch {
            syncManager.refresh()
        }
    }
}
