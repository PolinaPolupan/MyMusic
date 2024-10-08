package com.example.mymusic.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin() = navigate(com.example.mymusic.feature.login.LOGIN_ROUTE)

fun NavGraphBuilder.loginScreen(onNavigateToHome: () -> Unit) {
    composable(
        route = com.example.mymusic.feature.login.LOGIN_ROUTE
    ) {
        LoginScreen(onNavigateToHome = onNavigateToHome)
    }
}