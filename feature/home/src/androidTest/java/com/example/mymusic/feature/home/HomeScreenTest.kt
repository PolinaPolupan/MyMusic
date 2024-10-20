package com.example.mymusic.feature.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.designsystem.theme.MyMusicTheme
import com.example.mymusic.core.model.Track
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder


@HiltAndroidTest
class HomeScreenTest {

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
                HomeContent(
                    uiState = HomeUiState.Loading,
                    authenticatedUiState = AuthenticatedUiState.Loading,
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
                )
            }
        }

        composeTestRule
            .onNodeWithTag("home:topPicksLoading")
            .assertExists()

        composeTestRule
            .onNodeWithTag("home:recentlyPlayedLoading")
            .assertExists()

        composeTestRule
            .onNodeWithTag("home:blurredImageHeaderLoading")
            .assertExists()
    }

    @Test
    fun blurredImageHeader_whenScreenIsLoaded_existsAndShowsCorrectImage() {
        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Success(topPicks = PreviewParameterData.tracks),
                    authenticatedUiState = AuthenticatedUiState.Success(
                        ""
                    ),
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
                )
            }
        }

        val initialPageInd = PreviewParameterData.tracks.size * 100 / 2  % PreviewParameterData.tracks.size
        val initialImageUrl = PreviewParameterData.tracks[initialPageInd].album.imageUrl

        composeTestRule
            .onNodeWithTag("home:blurredImageHeader")
            .assertExists()

        composeTestRule
            .onNodeWithTag(initialImageUrl)
            .assertExists()
    }

    @Test
    fun topPicks_whenScreenIsLoaded_existsAndShowsRecommendations() {
        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Success(topPicks = PreviewParameterData.tracks),
                    authenticatedUiState = AuthenticatedUiState.Success(
                        ""
                    ),
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
                )
            }
        }

        val size = PreviewParameterData.tracks.size
        val initialPageInd = size * 100 / 2 // Set the initial page to the middle of the list

        composeTestRule
            .onNodeWithTag("home:topPicks")
            .assertExists()

        // Swipe to left and assert that the tracks are displayed
        for (ind in 0.. size * 2) {

            composeTestRule
                .onNodeWithText(PreviewParameterData.tracks[(initialPageInd + ind) % size].name)
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithTag("home:topPicks")
                .performScrollToIndex(initialPageInd + ind)
        }

        // Swipe to right and assert that the tracks are displayed
        for (ind in 0.. size * 2) {

            composeTestRule
                .onNodeWithText(PreviewParameterData.tracks[(initialPageInd - ind) % size].name)
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithTag("home:topPicks")
                .performScrollToIndex(initialPageInd - ind)
        }
    }

    @Test
    fun recentlyPlayed_whenScreenIsLoaded_existsAndShowsTracks() {
        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Success(topPicks = emptyList()),
                    authenticatedUiState = AuthenticatedUiState.Success(
                        ""
                    ),
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(PreviewParameterData.tracks)).collectAsLazyPagingItems()
                )
            }
        }

        val size = PreviewParameterData.tracks.size

        composeTestRule
            .onNodeWithTag("home:recentlyPlayed")
            .assertExists()

        // Swipe to left and assert that the tracks are displayed
        for (ind in 0..<size) {

            composeTestRule
                .onNodeWithText(PreviewParameterData.tracks[ind].name)
                .assertIsDisplayed()

            composeTestRule
                .onNodeWithTag("home:recentlyPlayed")
                .performScrollToIndex(ind)
        }
    }

    @Test
    fun blurredImageHeader_whenScrollToLeft_imageChangesWithAnimation() {

        composeTestRule.mainClock.autoAdvance = false // Pause animations

        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Success(topPicks = PreviewParameterData.tracks),
                    authenticatedUiState = AuthenticatedUiState.Success(
                        ""
                    ),
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
                )
            }
        }

        val initialPageInd = PreviewParameterData.tracks.size * 100 / 2

        composeTestRule
            .onNodeWithTag("home:blurredImageHeader")
            .assertExists()

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag(PreviewParameterData.tracks[initialPageInd % PreviewParameterData.tracks.size].album.imageUrl)
            .assertIsDisplayed()

        // Scroll to the next item
        composeTestRule
            .onNodeWithTag("home:topPicks")
            .performScrollToIndex((initialPageInd + 1) % PreviewParameterData.tracks.size)

        composeTestRule.mainClock.advanceTimeBy(1600) // Advance time to load animation

        composeTestRule
            .onNodeWithTag(PreviewParameterData.tracks[(initialPageInd + 1) % PreviewParameterData.tracks.size].album.imageUrl)
            .assertIsDisplayed()
    }
}