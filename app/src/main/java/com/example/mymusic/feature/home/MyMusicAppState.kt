package com.example.mymusic.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mymusic.core.model.User
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.feature.addToPlaylist.ADD_TO_PLAYLIST_ROUTE
import com.example.mymusic.feature.library.LIBRARY_ROUTE
import com.example.mymusic.feature.library.navigateToLibrary
import com.example.mymusic.feature.player.PLAYER_ROUTE
import com.example.mymusic.feature.search.SEARCH_ROUTE
import com.example.mymusic.feature.search.navigateToSearch
import com.example.mymusic.navigation.TopLevelDestination

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
    val currentTrack = PreviewParameterData.tracks[0]

    /**
     * [_fullScreenDestinationRoutes] list is needed in order to check whether or not the BottomAppBar with the Player card
     * should be shown or not
     */
    private val _fullScreenDestinationRoutes: List<String> = listOf(
        PLAYER_ROUTE, ADD_TO_PLAYLIST_ROUTE
    )
    val user = User("0", "Polina", "")

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowBottomBar: Boolean @Composable get() = !currentDestination.isFullScreenDestinationInHierarchy(_fullScreenDestinationRoutes)

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

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

/**
 * [isFullScreenDestinationInHierarchy] checks whether or not such destinations' routes like [PLAYER_ROUTE] or [ADD_TO_PLAYLIST_ROUTE]
 * are in the hierarchy
 *
 * @param [destinationRoutes] list of "full screen" routes
 */
fun NavDestination?.isFullScreenDestinationInHierarchy(destinationRoutes: List<String>): Boolean {
    destinationRoutes.forEach { destination ->
        if (this?.hierarchy?.any {
                it.route?.contains(destination, true) == true
            } == true) return true
    }
    return false
}
