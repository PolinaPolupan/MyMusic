package com.example.mymusic.feature.addtoplaylist

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
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.component.SortOption
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.core.model.SimplifiedPlaylist
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddToPlaylistScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun placeholders_whenScreenIsLoading_exist() {
        composeTestRule.setContent {
            MyMusicTheme {
                AddToPlayListContent(
                    uiState = AddToPlaylistUiState.Loading,
                    playlists = flowOf(PagingData.from(emptyList<SimplifiedPlaylist>())).collectAsLazyPagingItems(),
                    onBackPress = {},
                    onSortOptionChanged = {},
                    currentSortOption = SortOption.CREATOR
                )
            }
        }

        composeTestRule
            .onNode(hasAnyChild(hasTestTag("addToPlaylist:playlistsLoading")))
            .assertIsDisplayed()
            .assertHasNoClickAction()
    }

    @Test
    fun playlists_whenScreenIsLoaded_existAndShowUsersPlaylists() {
        composeTestRule.setContent {
            MyMusicTheme {
                AddToPlayListContent(
                    uiState = AddToPlaylistUiState.Success(
                        track = PreviewParameterData.tracks[0]
                    ),
                    playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
                    onBackPress = {},
                    onSortOptionChanged = {},
                    currentSortOption = SortOption.CREATOR
                )
            }
        }

        PreviewParameterData.simplifiedPlaylists.forEach { playlist ->
            composeTestRule
                .onNodeWithTag("addToPlaylist:items")
                .performScrollToNode(hasText(playlist.name))

            composeTestRule
                .onNode(hasText(playlist.name))
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun topApBar_whenScrolledDown_isDisplayed() {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            MyMusicTheme {
                AddToPlayListContent(
                    uiState = AddToPlaylistUiState.Success(
                        track = PreviewParameterData.tracks[0]
                    ),
                    playlists = flowOf(PagingData.from(PreviewParameterData.simplifiedPlaylists)).collectAsLazyPagingItems(),
                    onBackPress = {},
                    onSortOptionChanged = {},
                    currentSortOption = SortOption.CREATOR
                )
            }
        }

        composeTestRule
            .onNodeWithTag("addToPlaylist:items")
            .performScrollToIndex(PreviewParameterData.simplifiedPlaylists.size)

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag("addToPlaylist:topAppBar")
            .assert(hasAnyChild(hasText("Add to playlist")))
            .assertIsDisplayed()
    }
}