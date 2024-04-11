package com.example.mymusic.feature.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.mymusic.R
import com.example.mymusic.core.designSystem.component.MyMusicNavigationBarItem
import com.example.mymusic.core.ui.PlayerCard
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.core.designSystem.util.darker
import com.example.mymusic.feature.player.navigateToPlayer
import com.example.mymusic.navigation.MyMusicNavHost
import com.example.mymusic.navigation.TopLevelDestination
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@SuppressLint("NewApi")
@Composable
fun MyMusicApp(
    appState: MyMusicAppState = rememberMyMusicAppState()
) {
    // Is used to show the content behind PlayerCard.
    val hazeState = remember { HazeState() }

    val destination = appState.currentTopLevelDestination

    val currentTrack = appState.currentTrack
    val user = appState.user

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
           .haze(
                state = hazeState
            )
        ) {
            MyMusicNavHost(
                appState = appState
            )
        }
        AnimatedVisibility(
            visible = destination != null,
            enter = fadeIn(initialAlpha = 0.0f),
            exit = fadeOut()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Show on top level destinations.
                PlayerCard(
                    coverUrl = currentTrack.album.imageUrl,
                    name = currentTrack.name,
                    artistName = currentTrack.artists[0].name,
                    isPaused = true,
                    onClick = { appState.navController.navigateToPlayer(currentTrack.id) },
                    modifier = Modifier
                        /* TODO: Performance bug during exiting animations */
                        .hazeChild(
                            state = hazeState,
                            shape = MaterialTheme.shapes.medium,
                            style = HazeStyle(tint = MaterialTheme.colorScheme.surfaceTint.darker(0.8f).copy(alpha = 0.4f))
                        )
                )
                BottomNavigationBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
            .height(dimensionResource(id = R.dimen.bottom_bar_height))
            .linearGradientScrim(
                color = Color.Black,
                startYPercentage = 0f,
                endYPercentage = 1f,
                start = Offset(0f, 0f),
                end = Offset(0f, 150f),
                decay = 1f
            )
    )
    {
        /* TODO: Think about creating a custom bottom navigation bar component */
        NavigationBar(
            modifier = Modifier,
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
                MyMusicNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(stringResource(destination.iconTextId)) },
                    modifier = Modifier,
                )
            }
        }
    }
}

fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false

/* TODO: Make Bottom Navigation Bar preview work
@Preview
@Composable
fun BottomNavigationBarPreview() {
    val items = listOf(TopLevelDestination.HOME, TopLevelDestination.SEARCH, TopLevelDestination.LIBRARY)
    BottomNavigationBar(
        destinations = items,
        currentDestination = TopLevelDestination.HOME,
        onNavigateToDestination = {}
    )
}
*/