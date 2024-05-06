package com.example.mymusic.feature.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
    val searchQuery: String = ""
    val recentSearches: List<String> = listOf()
    fun onSearchTriggered(query: String) {

    }
    fun onSearchQueryChanged(query: String) {

    }
}