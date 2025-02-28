package com.example.mymusic.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.mymusic.feature.addtoplaylist.addToPlaylistScreen
import com.example.mymusic.feature.addtoplaylist.navigateToAddToPlaylist
import com.example.mymusic.feature.album.albumScreen
import com.example.mymusic.feature.album.navigateToAlbum
import com.example.mymusic.MyMusicAppState
import com.example.mymusic.feature.home.homeScreen
import com.example.mymusic.feature.home.navigateToHome
import com.example.mymusic.feature.library.libraryScreen
import com.example.mymusic.feature.login.loginScreen
import com.example.mymusic.feature.login.navigateToLogin
import com.example.mymusic.feature.player.navigateToPlayer
import com.example.mymusic.feature.player.playerScreen
import com.example.mymusic.feature.playlist.navigateToPlaylist
import com.example.mymusic.feature.playlist.playlistScreen
import com.example.mymusic.feature.search.searchScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyMusicNavHost(
    appState: MyMusicAppState,
    modifier: Modifier = Modifier,
    startDestination: String = com.example.mymusic.feature.home.HOME_ROUTE,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(onNavigateToLogin = navController::navigateToLogin)
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
            onNavigateToAlbum = navController::navigateToAlbum
        )
        addToPlaylistScreen(onBackClick = navController::popBackStack)
        albumScreen(onBackClick = navController::popBackStack)
        playlistScreen(onBackClick = navController::popBackStack)
    }
}