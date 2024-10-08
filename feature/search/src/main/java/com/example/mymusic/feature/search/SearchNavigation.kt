package com.example.mymusic.feature.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions) = navigate(com.example.mymusic.feature.search.SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen() {
    composable(
        route = com.example.mymusic.feature.search.SEARCH_ROUTE
    ) {
        SearchScreen()
    }
}