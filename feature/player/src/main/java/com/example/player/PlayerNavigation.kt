package com.example.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val PLAYER_ROUTE = "player_route"

@VisibleForTesting
internal const val TRACK_ID_ARG = "trackId"

fun NavController.navigateToPlayer(trackId: String) {
    navigate("${PLAYER_ROUTE}/$trackId") {
        launchSingleTop = true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.playerScreen(
    onAddToPlaylistClick: (String) -> Unit,
    onNavigateToAlbum: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = "${PLAYER_ROUTE}/{${TRACK_ID_ARG}}",
        arguments = listOf(
            navArgument(TRACK_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        PlayerScreen(onBackClick = onBackClick, onAddToPlaylistClick = onAddToPlaylistClick, onNavigateToAlbum = onNavigateToAlbum)
    }
}