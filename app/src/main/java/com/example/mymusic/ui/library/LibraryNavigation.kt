package com.example.mymusic.ui.library

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.mymusic.ui.home.HOME_ROUTE
import com.example.mymusic.ui.home.Home

const val LIBRARY_ROUTE = "library_route"

fun NavController.navigateToLibrary(navOptions: NavOptions) = navigate(LIBRARY_ROUTE, navOptions)

fun NavGraphBuilder.libraryScreen() {
    composable(
        route = LIBRARY_ROUTE
    ) {
        Library()
    }
}