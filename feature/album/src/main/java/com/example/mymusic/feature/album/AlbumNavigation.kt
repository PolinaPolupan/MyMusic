package com.example.mymusic.feature.album

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.fadeIn
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ALBUM_ROUTE = "album_route"

@VisibleForTesting
internal const val ALBUM_ID_ARG = "albumId"

fun NavController.navigateToAlbum(albumId: String) {
    navigate("${ALBUM_ROUTE}/$albumId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.albumScreen(onBackClick: () -> Unit) {
    composable(
        enterTransition = {fadeIn()},
        route = "${ALBUM_ROUTE}/{${ALBUM_ID_ARG}}",
        arguments = listOf(
            navArgument(ALBUM_ID_ARG) { type = NavType.StringType },
        )
    ) {
        AlbumScreen(onBackClick = onBackClick)
    }
}