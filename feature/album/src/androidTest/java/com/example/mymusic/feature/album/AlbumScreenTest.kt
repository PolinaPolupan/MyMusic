package com.example.mymusic.feature.album

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
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.designsystem.component.OneOf
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.component.TracksListUiState
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun placeholders_whenScreenIsLoading_exist() {
        composeTestRule.setContent {
            MyMusicTheme {
                com.example.mymusic.feature.album.AlbumContent(
                    uiState = TracksListUiState.Loading,
                    onTrackClick = {},
                    onBackClick = {},
                    onSettingsClick = {}
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
    fun tracks_whenScreenIsLoaded_existAndShowAlbumsTracks() {
        composeTestRule.setContent {
            MyMusicTheme {
                com.example.mymusic.feature.album.AlbumContent(
                    uiState = TracksListUiState.Success(item = OneOf(album = PreviewParameterData.albums[0])),
                    onTrackClick = {},
                    onBackClick = {},
                    onSettingsClick = {}
                )
            }
        }

        PreviewParameterData.albums[0].tracks.forEach { track ->
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
                com.example.mymusic.feature.album.AlbumContent(
                    uiState = TracksListUiState.Success(item = OneOf(album = PreviewParameterData.albums[0])),
                    onTrackClick = {},
                    onBackClick = {},
                    onSettingsClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("tracksList:lazyColumn")
            .performScrollToIndex(PreviewParameterData.albums[0].tracks.size)

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag("tracksList:topAppBar")
            .assert(hasAnyChild(hasText(PreviewParameterData.albums[0].name)))
            .assertIsDisplayed()
    }
}