package com.example.mymusic.feature.player

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@VisibleForTesting
internal const val TRACK_ID_ARG = "trackId"

fun NavController.navigateToPlayer(trackId: String) {
    navigate("player_route/$trackId") {
        launchSingleTop = true
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.playerScreen(
    onAddToPlaylistClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(
        route = "player_route/{$TRACK_ID_ARG}",
        arguments = listOf(
            navArgument(TRACK_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        Player(onBackClick = onBackClick, onAddToPlaylistClick = onAddToPlaylistClick)
    }
}