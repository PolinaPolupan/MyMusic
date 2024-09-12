package com.example.mymusic.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.addtoplaylist.addToPlaylistScreen
import com.example.addtoplaylist.navigateToAddToPlaylist
import com.example.album.albumScreen
import com.example.album.navigateToAlbum
import com.example.mymusic.MyMusicAppState
import com.example.home.homeScreen
import com.example.home.navigateToHome
import com.example.library.libraryScreen
import com.example.login.loginScreen
import com.example.login.navigateToLogin
import com.example.player.navigateToPlayer
import com.example.player.playerScreen
import com.example.playlist.navigateToPlaylist
import com.example.playlist.playlistScreen
import com.example.search.searchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyMusicNavHost(
    appState: MyMusicAppState,
    modifier: Modifier = Modifier,
    startDestination: String = com.example.home.HOME_ROUTE,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            onTrackClick = navController::navigateToPlayer,
            onNavigateToLogin = navController::navigateToLogin
        )
        loginScreen(onNavigateToHome = navController::navigateToHome)
        searchScreen()
        libraryScreen(
            onPlaylistClick = navController::navigateToPlaylist,
            onAlbumClick = navController::navigateToAlbum,
            onNavigateToLogin = navController::navigateToLogin
        )
        playerScreen(
            onBackClick = navController::popBackStack,
            onAddToPlaylistClick = navController::navigateToAddToPlaylist,
            onNavigateToAlbum = navController::navigateToAlbum)

        addToPlaylistScreen(onBackClick = navController::popBackStack)
        albumScreen(
            onBackClick = navController::popBackStack,
            onNavigateToPlayer = navController::navigateToPlayer
        )
        playlistScreen(onBackClick = navController::popBackStack)
    }
}