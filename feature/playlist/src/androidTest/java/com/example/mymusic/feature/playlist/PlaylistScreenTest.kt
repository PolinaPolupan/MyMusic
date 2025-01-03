package com.example.mymusic.feature.playlist

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
import com.example.mymusic.core.designsystem.component.OneOf
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.component.TracksListUiState
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@HiltAndroidTest
class PlaylistScreenTest {

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @BindValue
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun placeholders_whenScreenIsLoading_exist() {
        composeTestRule.setContent {
            MyMusicTheme {
                PlaylistContent(
                    uiState = TracksListUiState.Loading,
                    onTrackClick = {},
                    onSettingsClick = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNode(hasAnyChild(hasTestTag("tracksList:itemsLoading")))
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag("tracksList:nameTextPlaceholder")
            .assertIsDisplayed()
            .assertHasNoClickAction()

        composeTestRule
            .onNodeWithTag("tracksList:descriptionTextPlaceholder")
            .assertIsDisplayed()
            .assertHasNoClickAction()

        // Set "assertExists" because top app bar content is not displayed when the tracks list is not scrolled down (content alpha is equal to zero)
        composeTestRule
            .onNodeWithTag("tracksList:topAppBarLoading")
            .assertExists()
            .assertHasNoClickAction()
    }

    @Test
    fun tracks_whenScreenIsLoaded_existAndShowPlaylistsTracks() {
        composeTestRule.setContent {
            MyMusicTheme {
                PlaylistContent(
                    uiState = TracksListUiState.Success(item = OneOf(playlist = PreviewParameterData.playlists[0])),
                    onTrackClick = {},
                    onSettingsClick = {},
                    onBackClick = {}
                )
            }
        }

        PreviewParameterData.playlists[0].tracks.forEach { track ->
            composeTestRule
                .onNodeWithTag("tracksList:lazyColumn")
                .performScrollToNode(hasText(track.name))

            composeTestRule
                .onNodeWithText(track.name)
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun topApBar_whenScrolledDown_isDisplayed() {

        composeTestRule.mainClock.autoAdvance = false

        composeTestRule.setContent {
            MyMusicTheme {
                PlaylistContent(
                    uiState = TracksListUiState.Success(item = OneOf(playlist = PreviewParameterData.playlists[0])),
                    onTrackClick = {},
                    onSettingsClick = {},
                    onBackClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("tracksList:lazyColumn")
            .performScrollToIndex(PreviewParameterData.playlists[0].tracks.size)

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag("tracksList:topAppBar")
            .assert(hasAnyChild(hasText(PreviewParameterData.playlists[0].name)))
            .assertIsDisplayed()
    }
}