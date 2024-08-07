package com.example.mymusic.feature

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.feature.home.HomeContent
import com.example.mymusic.feature.home.HomeUiState
import com.example.mymusic.model.Track
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun placeholders_whenScreenIsLoading_exist() {
        composeTestRule.setContent {
            MyMusicTheme {
                HomeContent(
                    uiState = HomeUiState.Loading,
                    userImageUrl = "",
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
}