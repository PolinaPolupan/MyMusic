package com.example.mymusic.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() = navigate(HOME_ROUTE)

fun NavGraphBuilder.homeScreen(onNavigateToLogin: () -> Unit) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreen(onNavigateToLogin = onNavigateToLogin)
    }
}