package com.example.mymusic.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.example.mymusic.feature.addToPlaylist.addToPlaylistScreen
import com.example.mymusic.feature.addToPlaylist.navigateToAddToPlaylist
import com.example.mymusic.feature.album.albumScreen
import com.example.mymusic.feature.album.navigateToAlbum
import com.example.mymusic.feature.home.HOME_ROUTE
import com.example.mymusic.MyMusicAppState
import com.example.mymusic.feature.home.homeScreen
import com.example.mymusic.feature.library.libraryScreen
import com.example.mymusic.feature.login.LOGIN_ROUTE
import com.example.mymusic.feature.login.loginScreen
import com.example.mymusic.feature.player.navigateToPlayer
import com.example.mymusic.feature.player.playerScreen
import com.example.mymusic.feature.playlist.navigateToPlaylist
import com.example.mymusic.feature.playlist.playlistScreen
import com.example.mymusic.feature.search.searchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyMusicNavHost(
    appState: MyMusicAppState,
    startDestination: String = LOGIN_ROUTE,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(onTrackClick = navController::navigateToPlayer, navController =  navController)
        loginScreen(navController = navController)
        searchScreen()
        libraryScreen(onPlaylistClick = navController::navigateToPlaylist, onAlbumClick = navController::navigateToAlbum)
        playerScreen(onBackClick = navController::popBackStack, onAddToPlaylistClick = navController::navigateToAddToPlaylist, onNavigateToAlbum = navController::navigateToAlbum)
        addToPlaylistScreen(onBackClick = navController::popBackStack)
        albumScreen(onBackClick = navController::popBackStack)
        playlistScreen(onBackClick = navController::popBackStack)
    }
}