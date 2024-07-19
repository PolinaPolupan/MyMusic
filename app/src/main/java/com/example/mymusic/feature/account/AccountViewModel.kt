package com.example.mymusic.feature.account

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.AuthorizationManager
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authorizationManager: AuthorizationManager,
    userDataRepository: UserDataRepository
): ViewModel() {

    val uiState: StateFlow<AccountUiState> =
        userDataRepository.userPreferencesFlow
            .map {
                AccountUiState.Success(
                    data = UserAccountData(
                        displayName = it.displayName,
                        email = it.email,
                        imageUrl = it.imageUrl
                    )
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AccountUiState.Loading)

    fun signIn(): Intent {
        return authorizationManager.signIn()
    }

    fun handleAuthorizationResponse(intent: Intent, onSuccess: () -> Unit) {
        authorizationManager.handleAuthorizationResponse(intent, onSuccess)
    }
}

data class UserAccountData(
    val displayName: String?,
    val email: String?,
    val imageUrl: String?
)

sealed interface AccountUiState {
    data object Loading: AccountUiState
    data class Success(val data: UserAccountData): AccountUiState
}