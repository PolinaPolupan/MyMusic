package com.example.mymusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.UserDataRepository
import com.example.mymusic.core.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {

    data class LoginActivityUiState (
        var showDialog: Boolean,
        var isSpotifyInstalled: Boolean
    )

    private val _showDialog = MutableStateFlow(false)
    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val uiState: StateFlow<LoginActivityUiState> = combine(
        _showDialog,
        _userDataFlow
    ) { showDialog: Boolean, userPreferences: UserPreferences ->
        return@combine LoginActivityUiState(showDialog, userPreferences.isSpotifyInstalled)
    }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), LoginActivityUiState(
            showDialog = false,
            isSpotifyInstalled = false
        ))

    fun setIsSpotifyInstalled(isInstalled: Boolean) {
        viewModelScope.launch {
            userDataRepository.updateIsSpotifyInstalled(isInstalled)
        }
    }

    fun setShowDialog(show: Boolean) {
        _showDialog.value = show
    }
}

