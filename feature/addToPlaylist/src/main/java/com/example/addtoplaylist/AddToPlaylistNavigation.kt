package com.example.addtoplaylist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ADD_TO_PLAYLIST_ROUTE = "add_to_playlist_route"

internal const val TRACK_ID_ARG = "trackId"

fun NavController.navigateToAddToPlaylist(trackId: String) {
    navigate("${ADD_TO_PLAYLIST_ROUTE}/$trackId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.addToPlaylistScreen(onBackClick: () -> Unit) {
    composable(
        route = "${ADD_TO_PLAYLIST_ROUTE}/{${TRACK_ID_ARG}}",
        arguments = listOf(
            navArgument(TRACK_ID_ARG) { type = NavType.StringType }
        )
    ) {backStackEntry ->
        val id = backStackEntry.arguments?.getString(TRACK_ID_ARG)
        /*TODO: NULL CHECK */
        AddToPlayListScreen(onBackPress = onBackClick)
    }
}