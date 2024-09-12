package com.example.library

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val LIBRARY_ROUTE = "library_route"

fun NavController.navigateToLibrary(navOptions: NavOptions) = navigate(com.example.library.LIBRARY_ROUTE, navOptions)

fun NavGraphBuilder.libraryScreen(
    onPlaylistClick: (String) -> Unit,
    onAlbumClick: (String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable(
        route = com.example.library.LIBRARY_ROUTE
    ) {
        LibraryScreen(onNavigateToPlaylist = onPlaylistClick, onNavigateToAlbum = onAlbumClick, onNavigateToLogin = onNavigateToLogin)
    }
}