package com.example.mymusic.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(onTrackClick: (String) -> Unit) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreen(onTrackClick = onTrackClick)
    }
}