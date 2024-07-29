package com.example.mymusic.core.designSystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.compose.rememberNavController
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.isTopLevelDestinationInHierarchy
import com.example.mymusic.navigation.TopLevelDestination

@Composable
fun RowScope.BottomNavigationBarItem(
    onClick: () -> Unit,
    destination: TopLevelDestination,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier
) {
    val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

    val alpha: Float by animateFloatAsState(if (selected) 1f else 0.5f, label = "bottomNavigationItem")

    val contentColor =
        if (selected) Color.White else Color.White

    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                contentDescription = "icon",
                tint = contentColor,
                modifier = Modifier.size(30.dp).alpha(alpha)
            )
            Text(
                text = stringResource(id = destination.titleTextId),
                color = contentColor,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.alpha(alpha)
            )
        }
    }
}

@Preview
@Composable
fun BottomNavigationItemPreview() {
    val destinations = listOf(TopLevelDestination.HOME, TopLevelDestination.SEARCH, TopLevelDestination.LIBRARY)
    val currentDestination = rememberNavController().currentDestination
    MyMusicTheme {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            destinations.forEach { destination ->
                BottomNavigationBarItem(
                    onClick = { },
                    destination = destination,
                    currentDestination = currentDestination
                )
            }
        }
    }
}