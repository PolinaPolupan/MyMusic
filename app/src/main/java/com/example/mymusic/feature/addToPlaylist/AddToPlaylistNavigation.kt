package com.example.mymusic.feature.addToPlaylist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

internal const val TRACK_ID_ARG = "trackId"

fun NavController.navigateToAddToPlaylist(trackId: String) {
    navigate("add_to_playlist_route/$trackId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.addToPlaylistScreen(onBackClick: () -> Unit) {
    composable(
        route = "add_to_playlist_route/{${TRACK_ID_ARG}}",
        arguments = listOf(
            navArgument(TRACK_ID_ARG) { type = NavType.StringType }
        )
    ) {backStackEntry ->
        val id = backStackEntry.arguments?.getString(TRACK_ID_ARG)
        /*TODO: NULL CHECK */
        AddToPlayListScreen(trackId = id ?: "0", onBackPress = onBackClick)
    }
}