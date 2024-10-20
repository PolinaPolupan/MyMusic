package com.example.mymusic

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@HiltAndroidTest
class NavigationTest {

    /**
     * Manages the components' state and is used to perform injection on your test
     */
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    /**
     * Create a temporary folder used to create a Data Store file. This guarantees that
     * the file is removed in between each test, preventing a crash.
     */
    @BindValue
    @get:Rule(order = 1)
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()


    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() = hiltRule.inject()

    /**
     * Navigation test asserts that the first destination is Home screen.
     */
    @Test
    fun appNavHost_firstScreenIsHome() {

        composeTestRule
            .onNodeWithText("Listen Now")
            .assertIsDisplayed()
    }

    @Test
    fun navigationBar_navigatesToLibrary() {

        composeTestRule.mainClock.autoAdvance = false // Pause animations

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

        composeTestRule
            .onNodeWithText("Search")
            .performClick()

        composeTestRule
            .onNodeWithTag("search")
            .assertIsDisplayed()
    }

    @Test
    fun whenAccountDialogDismissed_previousScreenIsDisplayed() {

        composeTestRule
            .onNodeWithTag("accountIcon")
            .assertIsDisplayed()
    }
}