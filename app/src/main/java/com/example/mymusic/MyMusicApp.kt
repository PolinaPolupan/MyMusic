package com.example.mymusic

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.mymusic.core.ui.BottomNavigationBarItem
import com.example.mymusic.core.ui.PlayerCard
import com.example.mymusic.core.designSystem.component.linearGradientScrim
import com.example.mymusic.feature.player.navigateToPlayer
import com.example.mymusic.navigation.MyMusicNavHost
import com.example.mymusic.navigation.TopLevelDestination

@SuppressLint("NewApi")
@Composable
fun MyMusicApp(
    appState: MyMusicAppState = rememberMyMusicAppState()
) {
    val currentTrack = appState.currentTrack

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
        ) {
            MyMusicNavHost(
                appState = appState
            )
        }
        AnimatedVisibility(
            visible = appState.shouldShowBottomBar,
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
                    onClick = { appState.navController.navigateToPlayer(currentTrack.id) }
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
            .fillMaxWidth()
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            destinations.forEach { destination ->
                BottomNavigationBarItem(
                    onClick = { onNavigateToDestination(destination) },
                    destination = destination,
                    currentDestination = currentDestination,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val items = listOf(TopLevelDestination.HOME, TopLevelDestination.SEARCH, TopLevelDestination.LIBRARY)
     val currentDestination = rememberNavController().currentDestination
     BottomNavigationBar(
        destinations = items,
        currentDestination = currentDestination,
        onNavigateToDestination = {}
    )
}
