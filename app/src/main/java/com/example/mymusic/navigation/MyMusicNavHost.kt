package com.example.mymusic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.mymusic.ui.home.HOME_ROUTE
import com.example.mymusic.ui.home.MyMusicAppState
import com.example.mymusic.ui.home.homeScreen
import com.example.mymusic.ui.library.libraryScreen
import com.example.mymusic.ui.search.searchScreen

@Composable
fun MyMusicNavHost(
    appState: MyMusicAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen()
        searchScreen()
        libraryScreen()
    }
}