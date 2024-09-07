package com.example.mymusic

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mymusic.core.designSystem.component.PreviewParameterData
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.feature.home.AuthenticatedUiState
import com.example.mymusic.feature.home.HomeContent
import com.example.mymusic.feature.home.HomeUiState
import com.example.mymusic.model.Track
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() = hiltRule.inject()

    /**
     * Navigation test asserts that the first destination is Home screen.
     */
    @Test
    fun appNavHost_firstScreenIsHome() {

        // Animations need to be paused in order to load Home screen before Log in screen
        composeTestRule.mainClock.autoAdvance = false // Pause animations

        // Advance time by a small period before Log in screen appeears
        composeTestRule.mainClock.advanceTimeBy(100) // Advance time to load home screen

        composeTestRule
            .onNodeWithText("Listen Now")
            .assertIsDisplayed()
    }

    @Test
    fun navigationBar_navigatesToLibrary() {

        // Animations need to be paused in order to load Home screen before Log in screen
        composeTestRule.mainClock.autoAdvance = false // Pause animations

        // Advance time by a small period before Log in screen appeears
        composeTestRule.mainClock.advanceTimeBy(100) // Advance time to load home screen

        composeTestRule
            .onNodeWithText("Library")
            .performClick()

        composeTestRule.mainClock.advanceTimeBy(100) // Advance time to load library screen

        composeTestRule
            .onNodeWithTag("library")
            .assertIsDisplayed()
    }

    @Test
    fun navigationBar_navigatesToSearch() {

        // Animations need to be paused in order to load Home screen before Log in screen
        composeTestRule.mainClock.autoAdvance = false // Pause animations

        // Advance time by a small period before Log in screen appeears
        composeTestRule.mainClock.advanceTimeBy(100) // Advance time to load home screen

        composeTestRule
            .onNodeWithText("Search")
            .performClick()

        composeTestRule.mainClock.advanceTimeBy(100) // Advance time to load search screen

        composeTestRule
            .onNodeWithTag("search")
            .assertIsDisplayed()
    }

    @Test
    fun whenAccountDialogDismissed_previousScreenIsDisplayed() {

        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Success(topPicks = PreviewParameterData.tracks),
                    authenticatedUiState = AuthenticatedUiState.Success(""),
                    onTrackClick = {},
                    recentlyPlayed = flowOf(PagingData.from(emptyList<Track>())).collectAsLazyPagingItems()
                )
            }
        }

        composeTestRule
            .onNodeWithTag("accountIcon")
            .assertIsDisplayed()
    }
}