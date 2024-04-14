package com.example.mymusic

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.mymusic.core.ui.PreviewParameterData
import org.junit.Rule
import org.junit.Test

/* TODO: REWRITE!!! */
class AlbumScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun topAppBar_whenIsNotScrolled_showsTransparentTopAppBarBackground() {

        var value = mutableStateOf(0)
        composeTestRule.setContent {
            val album = PreviewParameterData.albums[0]

            /*val lazyListState = rememberLazyListState()
            val scrollState = rememberScrollState(state = lazyListState)
            value = scrollState
            MyMusicTheme {
               AlbumContent(
                   name = album.name,
                   imageUrl = album.imageUrl,
                   artists = album.artists,
                   tracks = album.tracks,
                   lazyListState = lazyListState,
                   scrollState = scrollState
               )
            }
            composeTestRule
                .onNodeWithTag("album:lazyColumn")
                .performScrollToIndex(10)

            assert(value.value > 0)*/
        }
    }
}