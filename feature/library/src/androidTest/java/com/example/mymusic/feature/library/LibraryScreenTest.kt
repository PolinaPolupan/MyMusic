package com.example.mymusic.feature.library

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.feature.home.AuthenticatedUiState
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.component.SortOption
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.core.model.SimplifiedAlbum
import com.example.mymusic.core.model.SimplifiedPlaylist
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@HiltAndroidTest
class LibraryScreenTest {

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @BindValue
    @get:Rule(order = 0)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun placeholders_whenScreenIsLoading_exist() {
        composeTestRule.setContent {
            MyMusicTheme {
                LibraryContent(
                    uiState = AuthenticatedUiState.Loading,
                    albums = flowOf(PagingData.from(emptyList<SimplifiedAlbum>())).collectAsLazyPagingItems(),
                    playlists = flowOf(PagingData.from(emptyList<SimplifiedPlaylist>())).collectAsLazyPagingItems(),
                    onSortOptionChanged = {},
                    onNavigateToPlaylist = {},
                    onNavigateToAlbumClick = {},
                    currentSortOption = SortOption.CREATOR,
                    onAlbumClick = {},
                    onPlaylistClick = {}
                )
            }
        }

        composeTestRule.onNode(
            hasAnyChild(hasTestTag("library:itemsLoading"))
        )
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    @Test
    fun albums_whenScreenIsLoaded_existAndShowUsersAlbums() {
        composeTestRule.setContent {
            MyMusicTheme {
                LibraryContent(
                    uiState = AuthenticatedUiState.Success(""),
                    albums = flowOf(PagingData.from(PreviewParameterData.simplifiedAlbums)).collectAsLazyPagingItems(),
                    playlists = flowOf(PagingData.from(emptyList<SimplifiedPlaylist>())).collectAsLazyPagingItems(),
                    onSortOptionChanged = {},
                    onNavigateToPlaylist = {},
                    onNavigateToAlbumClick = {},
                    currentSortOption = SortOption.CREATOR,
                    onAlbumClick = {},
                    onPlaylistClick = {}
                )
            }
        }

        PreviewParameterData.simplifiedAlbums.forEach { album ->
            composeTestRule
                .onNodeWithTag("library:items")
                .performScrollToNode(hasText(album.name))

            composeTestRule
                .onNodeWithText(album.name)
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun playlists_whenScreenIsLoaded_existAndShowUsersPlaylists() {
        composeTestRule.setContent {
            MyMusicTheme {
                LibraryContent(
                    uiState = AuthenticatedUiState.Success(""),
                    albums = flowOf(PagingData.from(emptyList<SimplifiedAlbum>())).collectAsLazyPagingItems(),
                    playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
                    onSortOptionChanged = {},
                    onNavigateToPlaylist = {},
                    onNavigateToAlbumClick = {},
                    currentSortOption = SortOption.CREATOR,
                    onAlbumClick = {},
                    onPlaylistClick = {}
                )
            }
        }

        PreviewParameterData.simplifiedPlaylists.forEach { playlist ->
            composeTestRule
                .onNodeWithTag("library:items")
                .performScrollToNode(hasText(playlist.name))

            composeTestRule
                .onNodeWithText(playlist.name)
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun topApBar_whenScrolledDown_isDisplayed() {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            MyMusicTheme {
                LibraryContent(
                    uiState = AuthenticatedUiState.Success(""),
                    albums = flowOf(PagingData.from(PreviewParameterData.simplifiedAlbums)).collectAsLazyPagingItems(),
                    playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
                    onSortOptionChanged = {},
                    onNavigateToPlaylist = {},
                    onNavigateToAlbumClick = {},
                    currentSortOption = SortOption.CREATOR,
                    onAlbumClick = {},
                    onPlaylistClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("library:items")
            .performScrollToIndex(PreviewParameterData.simplifiedPlaylists.size + PreviewParameterData.simplifiedAlbums.size)

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag("library:topAppBar")
            .assert(hasAnyChild(hasText("Your Library")))
            .assertIsDisplayed()
    }
}