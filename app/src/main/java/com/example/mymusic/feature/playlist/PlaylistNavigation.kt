package com.example.mymusic.feature.playlist

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymusic.feature.album.AlbumScreen

@VisibleForTesting
internal const val PLAYLIST_ID_ARG = "playlistId"

fun NavController.navigateToPlaylist(playlistId: String) {
    navigate("playlist_route/$playlistId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.playlistScreen(
    onBackClick: () -> Unit
) {
    composable(
        enterTransition = { fadeIn() },
        route = "playlist_route/{$PLAYLIST_ID_ARG}",
        arguments = listOf(
            navArgument(PLAYLIST_ID_ARG) { type = NavType.StringType },
        )
    ) {
        PlaylistScreen(onBackClick = onBackClick)
    }
}