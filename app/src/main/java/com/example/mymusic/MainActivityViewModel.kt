package com.example.mymusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
): ViewModel() {

    private val _userDataFlow = userDataRepository.userPreferencesFlow

    val uiState: StateFlow<MainActivityUiState> =
        _userDataFlow.map {
           MainActivityUiState.Success(
               UserData(it.authState)
           )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), MainActivityUiState.Loading)
}

data class UserData (
    var authState: String?
)

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
