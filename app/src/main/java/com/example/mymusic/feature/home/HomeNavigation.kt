package com.example.mymusic.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() = navigate(HOME_ROUTE)

fun NavGraphBuilder.homeScreen(onTrackClick: (String) -> Unit, navController: NavController) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreen(onTrackClick = onTrackClick, navController = navController)
    }
}