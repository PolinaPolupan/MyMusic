package com.example.mymusic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.mymusic.feature.addtoplaylist.ADD_TO_PLAYLIST_ROUTE
import com.example.mymusic.feature.home.navigateToHome
import com.example.mymusic.feature.library.navigateToLibrary
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.feature.player.PLAYER_ROUTE
import com.example.mymusic.feature.search.navigateToSearch
import com.example.mymusic.navigation.TopLevelDestination
import javax.inject.Inject

@Composable
fun rememberMyMusicAppState(
    navController: NavHostController = rememberNavController(),
) : MyMusicAppState {
    return remember(
        navController,
    ) {
        MyMusicAppState(
            navController = navController
        )
    }
}

class MyMusicAppState @Inject constructor(
    val navController: NavHostController,
) {
    val currentTrack = PreviewParameterData.tracks[0]

    /**
     * [_fullScreenDestinationRoutes] list is needed in order to check whether or not the BottomAppBar with the Player card
     * should be shown or not
     */
    private val _fullScreenDestinationRoutes: List<String> = listOf(
        com.example.mymusic.feature.player.PLAYER_ROUTE, com.example.mymusic.feature.addtoplaylist.ADD_TO_PLAYLIST_ROUTE,
        com.example.mymusic.feature.login.LOGIN_ROUTE
    )

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val shouldShowBottomBar: Boolean @Composable get() = !currentDestination.isFullScreenDestinationInHierarchy(_fullScreenDestinationRoutes)

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {  }
        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome()
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
