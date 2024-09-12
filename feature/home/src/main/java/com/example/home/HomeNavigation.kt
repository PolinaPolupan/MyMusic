package com.example.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() = navigate(com.example.home.HOME_ROUTE)

fun NavGraphBuilder.homeScreen(onTrackClick: (String) -> Unit, onNavigateToLogin: () -> Unit) {
    composable(
        route = com.example.home.HOME_ROUTE
    ) {
        HomeScreen(onTrackClick = onTrackClick, onNavigateToLogin = onNavigateToLogin)
    }
}