package com.example.mymusic.feature.album

import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mymusic.feature.library.LibraryScreen
import com.example.mymusic.feature.player.TRACK_ID_ARG

@VisibleForTesting
internal const val ALBUM_ID_ARG = "albumId"

fun NavController.navigateToAlbum(albumId: String) {
    navigate("album_route/$albumId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.albumScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = "album_route/{$ALBUM_ID_ARG}",
        arguments = listOf(
            navArgument(ALBUM_ID_ARG) { type = NavType.StringType },
        )
    ) {
        AlbumScreen()
    }
}