package com.example.mymusic.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.designsystem.component.MyMusicIcons
import com.example.mymusic.R

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */
enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = MyMusicIcons.Home,
        unselectedIcon = MyMusicIcons.HomeBorder,
        titleTextId = R.string.app_name,
    ),
    SEARCH(
        selectedIcon = MyMusicIcons.Search,
        unselectedIcon = MyMusicIcons.SearchBorder,
        titleTextId = R.string.search,
    ),
    LIBRARY(
        selectedIcon = MyMusicIcons.Library,
        unselectedIcon = MyMusicIcons.LibraryBorder,
        titleTextId = R.string.library,
    )
}
