package com.example.mymusic.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
): ViewModel() {

    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val uiState: StateFlow<AccountUiState> =
        _userDataFlow
            .map {
                AccountUiState(
                    it.displayName ?: "",
                    it.email ?: ""
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), AccountUiState("", ""))

    fun signOut() {
        /*TODO*/
    }
}

data class AccountUiState(
    val name: String,
    val email: String
)