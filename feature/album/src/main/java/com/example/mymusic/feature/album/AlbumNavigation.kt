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
    navigate("${com.example.mymusic.feature.album.ALBUM_ROUTE}/$albumId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.albumScreen(
    onNavigateToPlayer: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(
        enterTransition = {fadeIn()},
        route = "${com.example.mymusic.feature.album.ALBUM_ROUTE}/{${com.example.mymusic.feature.album.ALBUM_ID_ARG}}",
        arguments = listOf(
            navArgument(com.example.mymusic.feature.album.ALBUM_ID_ARG) { type = NavType.StringType },
        )
    ) {
        AlbumScreen(onBackClick = onBackClick, onNavigateToPlayer = onNavigateToPlayer)
    }
}