package com.example.playlist

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val PLAYLIST_ROUTE = "playlist_route"

@VisibleForTesting
internal const val PLAYLIST_ID_ARG = "playlistId"

fun NavController.navigateToPlaylist(playlistId: String) {
    navigate("${com.example.playlist.PLAYLIST_ROUTE}/$playlistId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.playlistScreen(
    onBackClick: () -> Unit
) {
    composable(
        enterTransition = { fadeIn() },
        route = "${com.example.playlist.PLAYLIST_ROUTE}/{${com.example.playlist.PLAYLIST_ID_ARG}}",
        arguments = listOf(
            navArgument(com.example.playlist.PLAYLIST_ID_ARG) { type = NavType.StringType },
        )
    ) {
        PlaylistScreen(onBackClick = onBackClick)
    }
}