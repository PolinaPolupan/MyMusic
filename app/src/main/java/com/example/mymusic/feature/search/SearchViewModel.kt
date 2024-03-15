package com.example.mymusic.feature.search

import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    val searchQuery: String = ""
    val recentSearches: List<String> = listOf()
    fun onSearchTriggered(query: String) {

    }
    fun onSearchQueryChanged(query: String) {

    }
}