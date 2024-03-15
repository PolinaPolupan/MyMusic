package com.example.mymusic.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.mymusic.feature.addToPlaylist.addToPlaylistScreen
import com.example.mymusic.feature.addToPlaylist.navigateToAddToPlaylist
import com.example.mymusic.feature.home.HOME_ROUTE
import com.example.mymusic.feature.home.MyMusicAppState
import com.example.mymusic.feature.home.homeScreen
import com.example.mymusic.feature.library.libraryScreen
import com.example.mymusic.feature.player.navigateToPlayer
import com.example.mymusic.feature.player.playerScreen
import com.example.mymusic.feature.search.searchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyMusicNavHost(
    appState: MyMusicAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(onTrackClick = navController::navigateToPlayer)
        searchScreen()
        libraryScreen()
        playerScreen(onBackClick = navController::popBackStack, onAddToPlaylistClick = navController::navigateToAddToPlaylist)
        addToPlaylistScreen(onBackClick = navController::popBackStack)
    }
}