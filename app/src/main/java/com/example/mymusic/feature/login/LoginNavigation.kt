package com.example.mymusic.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.mymusic.feature.home.HomeScreen

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin() = navigate(LOGIN_ROUTE)

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(
        route = LOGIN_ROUTE
    ) {
        LoginScreen(navController = navController)
    }
}