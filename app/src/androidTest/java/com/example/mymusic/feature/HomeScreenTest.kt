package com.example.mymusic.feature

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymusic.MainActivity
import com.example.mymusic.core.data.repository.MusicRepository
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.feature.home.HomeScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class HomeScreenTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var repository: MusicRepository

    @Before
    fun setup() {
        composeTestRule.setContent {
            MyMusicTheme {
                HomeScreen(onTrackClick = {}, onNavigateToLogin = {})
            }
        }
    }
}