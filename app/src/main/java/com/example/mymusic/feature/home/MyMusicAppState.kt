package com.example.mymusic.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.model.User
import com.example.mymusic.navigation.TopLevelDestination
import com.example.mymusic.feature.library.LIBRARY_ROUTE
import com.example.mymusic.feature.library.navigateToLibrary
import com.example.mymusic.feature.search.SEARCH_ROUTE
import com.example.mymusic.feature.search.navigateToSearch

@Composable
fun rememberMyMusicAppState(
    navController: NavHostController = rememberNavController(),
) : MyMusicAppState {
    return remember(
        navController,
    ) {
        MyMusicAppState(
            navController
        )
    }
}

class MyMusicAppState(
    val navController: NavHostController
) {
    val currentTrack = Track("0", "")
    val user = User("0", "Polina", "")

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_ROUTE -> TopLevelDestination.HOME
            SEARCH_ROUTE -> TopLevelDestination.SEARCH
            LIBRARY_ROUTE -> TopLevelDestination.LIBRARY
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {  }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
            TopLevelDestination.SEARCH -> navController.navigateToSearch(topLevelNavOptions)
            TopLevelDestination.LIBRARY -> navController.navigateToLibrary(topLevelNavOptions)
        }
    }
}