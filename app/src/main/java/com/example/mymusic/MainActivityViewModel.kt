package com.example.mymusic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymusic.core.data.repository.UserDataRepository
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

    val isPlaying: StateFlow<Boolean> = userDataRepository.userPreferencesFlow.map { it.isPlaying ?: false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun toggleIsPlaying(isPlaying: Boolean) {
        viewModelScope.launch {
            userDataRepository.setIsPlaying(isPlaying)
        }
    }
}