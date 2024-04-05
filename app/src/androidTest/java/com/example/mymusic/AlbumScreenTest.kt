package com.example.mymusic

import androidx.activity.ComponentActivity
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import com.example.mymusic.core.designSystem.theme.MyMusicTheme
import com.example.mymusic.core.ui.PreviewParameterData
import com.example.mymusic.core.ui.TrackCard
import com.example.mymusic.core.ui.rememberCurrentOffset
import com.example.mymusic.feature.album.TracksList
import org.junit.Rule
import org.junit.Test
import java.nio.file.WatchEvent.Modifier

class AlbumScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun topAppBar_whenIsNotScrolled_showsTransparentTopAppBarBackground() {

        var value = mutableStateOf(0)
        composeTestRule.setContent {
            val album = PreviewParameterData.albums[0]

            val lazyListState = rememberLazyListState()
            val scrollState = rememberCurrentOffset(state = lazyListState)
            value = scrollState
            MyMusicTheme {
               TracksList(
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

            assert(value.value > 0)
        }
    }
}