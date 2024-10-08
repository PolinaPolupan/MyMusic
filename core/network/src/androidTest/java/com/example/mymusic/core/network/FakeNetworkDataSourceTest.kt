package com.example.mymusic.core.network

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import com.example.mymusic.core.network.model.SpotifyPlayHistoryObject
import com.example.network.model.ExternalIds
import com.example.network.model.ExternalUrls
import com.example.network.model.SpotifyAlbum
import com.example.network.model.SpotifyArtist
import com.example.network.model.SpotifyImage
import com.example.network.model.SpotifySimplifiedArtist
import com.example.network.model.SpotifySimplifiedTrack
import com.example.network.model.SpotifyTrack
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@HiltAndroidTest
class FakeNetworkDataSourceTest {

    private lateinit var networkDataSource: FakeNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        networkDataSource = FakeNetworkDataSource(
            dispatcher = testDispatcher,
            context = ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun networkDataSource_deserializationOfRecommendations_deserializesCorrectly() = runTest(testDispatcher) {

        val recommendations = networkDataSource.getRecommendations()

        assertEquals(
            SpotifyTrack(
                album = SpotifyAlbum(
                    albumType = "ALBUM",
                    totalTracks = 14,
                    availableMarkets = listOf("CA", "MX", "US"),
                    externalUrls = ExternalUrls(
                        spotify = "https://open.spotify.com/album/3AWjk0oVV9YAY9r9boiffN"
                    ),
                    href = "https://api.spotify.com/v1/albums/3AWjk0oVV9YAY9r9boiffN",
                    id = "3AWjk0oVV9YAY9r9boiffN",
                    images = listOf(
                        SpotifyImage(
                            url = "https://i.scdn.co/image/ab67616d0000b273266539b00fd32cbcb7579f6c",
                            height = 640,
                            width = 640
                        ),
                        SpotifyImage(
                            url = "https://i.scdn.co/image/ab67616d00001e02266539b00fd32cbcb7579f6c",
                            height = 300,
                            width = 300
                        ),
                        SpotifyImage(
                            url = "https://i.scdn.co/image/ab67616d00004851266539b00fd32cbcb7579f6c",
                            height = 64,
                            width = 64
                        )
                    ),
                    name = "Sadnecessary (Bonus Track Version)",
                    releaseDate = "2014-10-14",
                    releaseDatePrecision = "day",
                    type = "album",
                    uri = "spotify:album:3AWjk0oVV9YAY9r9boiffN",
                    artists = listOf(
                        SpotifySimplifiedArtist(
                            externalUrls = ExternalUrls(
                                spotify = "https://open.spotify.com/artist/1hzfo8twXdOegF3xireCYs"
                            ),
                            href = "https://api.spotify.com/v1/artists/1hzfo8twXdOegF3xireCYs",
                            id = "1hzfo8twXdOegF3xireCYs",
                            name = "Milky Chance",
                            type = "artist",
                            uri = "spotify:artist:1hzfo8twXdOegF3xireCYs"
                        )
                    )
                ),
                artists = listOf(
                    SpotifyArtist(
                        externalUrls = ExternalUrls(
                            spotify = "https://open.spotify.com/artist/1hzfo8twXdOegF3xireCYs"
                        ),
                        href = "https://api.spotify.com/v1/artists/1hzfo8twXdOegF3xireCYs",
                        id = "1hzfo8twXdOegF3xireCYs",
                        name = "Milky Chance",
                        type = "artist",
                        uri = "spotify:artist:1hzfo8twXdOegF3xireCYs"
                    )
                ),
                availableMarkets = listOf("CA", "MX", "US"),
                discNumber = 1,
                durationMs = 313626,
                explicit = false,
                externalIds = ExternalIds(
                    isrc = "DEL211300741"
                ),
                externalUrls = ExternalUrls(
                    spotify = "https://open.spotify.com/track/0ZfByLXCeKchuj7zi1CJ0S"
                ),
                href = "https://api.spotify.com/v1/tracks/0ZfByLXCeKchuj7zi1CJ0S",
                id = "0ZfByLXCeKchuj7zi1CJ0S",
                isLocal = false,
                name = "Stolen Dance",
                popularity = 73,
                previewUrl = "https://p.scdn.co/mp3-preview/a5a49157caae8d8fbb5c82b70a6244a9ec828562?cid=cfe923b2d660439caf2b557b21f31221",
                trackNumber = 11,
                type = "track",
                uri = "spotify:track:0ZfByLXCeKchuj7zi1CJ0S"
            ),
            recommendations.first()
        )
    }

    @Test
    fun networkDataSource_deserializationOfRecentlyPlayed_deserializesCorrectly() = runTest(testDispatcher) {

        val recentlyPlayed = networkDataSource.getRecentlyPlayed("")

        Log.d("test", recentlyPlayed?.items?.first().toString())

        assertEquals(
            SpotifyPlayHistoryObject(
                track =  SpotifyTrack(
                    album = SpotifyAlbum(
                        albumType = "album",
                        totalTracks = 14,
                        availableMarkets = listOf(
                            "AR"
                        ),
                        externalUrls = ExternalUrls(
                            spotify = "https://open.spotify.com/album/1iCwsK0jUzBPQx7XEpumbz"
                        ),
                        href = "https://api.spotify.com/v1/albums/1iCwsK0jUzBPQx7XEpumbz",
                        id = "1iCwsK0jUzBPQx7XEpumbz",
                        images = listOf(
                            SpotifyImage(
                                url = "https://i.scdn.co/image/ab67616d0000b2732001bc1b4dd82d06631fcb9e",
                                height = 640,
                                width = 640
                            ),
                            SpotifyImage(
                                url = "https://i.scdn.co/image/ab67616d00001e022001bc1b4dd82d06631fcb9e",
                                height = 300,
                                width = 300
                            ),
                            SpotifyImage(
                                url = "https://i.scdn.co/image/ab67616d000048512001bc1b4dd82d06631fcb9e",
                                height = 64,
                                width = 64
                            )
                        ),
                        name = "Sadnecessary",
                        releaseDate = "2013-05-31",
                        releaseDatePrecision = "day",
                        type = "album",
                        uri = "spotify:album:1iCwsK0jUzBPQx7XEpumbz",
                        artists = listOf(
                            SpotifySimplifiedArtist(
                                externalUrls = ExternalUrls(
                                    spotify = "https://open.spotify.com/artist/1hzfo8twXdOegF3xireCYs"
                                ),
                                href = "https://api.spotify.com/v1/artists/1hzfo8twXdOegF3xireCYs",
                                id = "1hzfo8twXdOegF3xireCYs",
                                name = "Milky Chance",
                                type = "artist",
                                uri = "spotify:artist:1hzfo8twXdOegF3xireCYs"
                            )
                        )
                    ),
                    artists = listOf(
                        SpotifyArtist(
                            externalUrls = ExternalUrls(
                                spotify = "https://open.spotify.com/artist/1hzfo8twXdOegF3xireCYs"
                            ),
                            href = "https://api.spotify.com/v1/artists/1hzfo8twXdOegF3xireCYs",
                            id = "1hzfo8twXdOegF3xireCYs",
                            name = "Milky Chance",
                            type = "artist",
                            uri = "spotify:artist:1hzfo8twXdOegF3xireCYs"
                        )
                    ),
                    availableMarkets = listOf(
                        "AR"
                    ),
                    discNumber = 1,
                    durationMs = 313684,
                    explicit = false,
                    externalIds = ExternalIds(
                        isrc = "DEL211300741"
                    ),
                    externalUrls = ExternalUrls(
                        spotify = "https://open.spotify.com/track/34xGLuxM0rkxhCVyMSqwJO"
                    ),
                    href = "https://api.spotify.com/v1/tracks/34xGLuxM0rkxhCVyMSqwJO",
                    id = "34xGLuxM0rkxhCVyMSqwJO",
                    isLocal = false,
                    name = "Stolen Dance",
                    popularity = 79,
                    previewUrl = "https://p.scdn.co/mp3-preview/a5a49157caae8d8fbb5c82b70a6244a9ec828562?cid=cfe923b2d660439caf2b557b21f31221",
                    trackNumber = 11,
                    type = "track",
                    uri = "spotify:track:34xGLuxM0rkxhCVyMSqwJO"
                ),
                playedAt = "2024-10-07T17:13:59.219Z",
                context = null
            ),
            recentlyPlayed?.items?.first()
        )
    }

    @Test
    fun networkDataSource_deserializationOfAlbumTracks_deserializesCorrectly() = runTest(testDispatcher) {

        val albumTracks = networkDataSource.getAlbumTracks("")

        assertEquals(
            SpotifySimplifiedTrack(
                artists = listOf(
                    SpotifySimplifiedArtist(
                        externalUrls = ExternalUrls(
                            spotify = "https://open.spotify.com/artist/1hzfo8twXdOegF3xireCYs"
                        ),
                        href = "https://api.spotify.com/v1/artists/1hzfo8twXdOegF3xireCYs",
                        id = "1hzfo8twXdOegF3xireCYs",
                        name = "Milky Chance",
                        type = "artist",
                        uri =  "spotify:artist:1hzfo8twXdOegF3xireCYs"
                    )
                ),
                availableMarkets = listOf("AR"),
                discNumber = 1,
                durationMs = 313684,
                explicit = false,
                externalUrls = ExternalUrls(
                    spotify = "https://open.spotify.com/track/34xGLuxM0rkxhCVyMSqwJO"
                ),
                href = "https://api.spotify.com/v1/tracks/34xGLuxM0rkxhCVyMSqwJO",
                id = "34xGLuxM0rkxhCVyMSqwJO",
                isLocal = false,
                name = "Stolen Dance",
                previewUrl = "https://p.scdn.co/mp3-preview/a5a49157caae8d8fbb5c82b70a6244a9ec828562?cid=cfe923b2d660439caf2b557b21f31221",
                trackNumber = 11,
                type = "track",
                uri = "spotify:track:34xGLuxM0rkxhCVyMSqwJO"
            ),
            albumTracks.get(10)
        )
    }
}