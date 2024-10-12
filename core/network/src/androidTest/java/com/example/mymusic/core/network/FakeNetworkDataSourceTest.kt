package com.example.mymusic.core.network

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import com.example.mymusic.core.network.model.SpotifyPlayHistoryObject
import com.example.mymusic.core.network.model.AddedBy
import com.example.mymusic.core.network.model.Copyright
import com.example.mymusic.core.network.model.ExternalIds
import com.example.mymusic.core.network.model.ExternalUrls
import com.example.mymusic.core.network.model.PlaylistTrack
import com.example.mymusic.core.network.model.SavedAlbum
import com.example.mymusic.core.network.model.SavedPlaylistResponse
import com.example.mymusic.core.network.model.SpotifyAlbum
import com.example.mymusic.core.network.model.SpotifyAlbumExtended
import com.example.mymusic.core.network.model.SpotifyAlbumTracks
import com.example.mymusic.core.network.model.SpotifyArtist
import com.example.mymusic.core.network.model.SpotifyImage
import com.example.mymusic.core.network.model.SpotifyOwner
import com.example.mymusic.core.network.model.SpotifySimplifiedArtist
import com.example.mymusic.core.network.model.SpotifySimplifiedPlaylist
import com.example.mymusic.core.network.model.SpotifySimplifiedTrack
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.SpotifyTracks
import com.example.mymusic.core.network.model.VideoThumbnail
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
            albumTracks[10]
        )
    }

    @Test
    fun networkDataSource_deserializationOfSavedAlbums_deserializesCorrectly() = runTest(testDispatcher) {

        val savedAlbums = networkDataSource.getSavedAlbums(0, 0)

        val albumExternalUrl = ExternalUrls(spotify = "https://open.spotify.com/album/6nxDQi0FeEwccEPJeNySoS")
        val artistExternalUrl = ExternalUrls(spotify = "https://open.spotify.com/artist/53XhwfbYqKCa1cC15pYq2q")

        // Create SpotifyImage objects
        val images = listOf(
            SpotifyImage(url = "https://i.scdn.co/image/ab67616d0000b273407bd04707c463bbb3410737", height = 640, width = 640),
            SpotifyImage(url = "https://i.scdn.co/image/ab67616d00001e02407bd04707c463bbb3410737", height = 300, width = 300),
            SpotifyImage(url = "https://i.scdn.co/image/ab67616d00004851407bd04707c463bbb3410737", height = 64, width = 64)
        )

        // Create SpotifySimplifiedArtist object
        val artist = SpotifySimplifiedArtist(
            externalUrls = artistExternalUrl,
            href = "https://api.spotify.com/v1/artists/53XhwfbYqKCa1cC15pYq2q",
            id = "53XhwfbYqKCa1cC15pYq2q",
            name = "Imagine Dragons",
            type = "artist",
            uri = "spotify:artist:53XhwfbYqKCa1cC15pYq2q"
        )

        // Create SpotifySimplifiedTrack objects
        val tracks = listOf(
            SpotifySimplifiedTrack(
                artists = listOf(artist),
                availableMarkets = listOf("AR"),
                discNumber = 1,
                durationMs = 186813,
                explicit = false,
                externalUrls = ExternalUrls(spotify = "https://open.spotify.com/track/62yJjFtgkhUrXktIoSjgP2"),
                href = "https://api.spotify.com/v1/tracks/62yJjFtgkhUrXktIoSjgP2",
                id = "62yJjFtgkhUrXktIoSjgP2",
                name = "Radioactive",
                trackNumber = 1,
                type = "track",
                uri = "spotify:track:62yJjFtgkhUrXktIoSjgP2",
                isLocal = false
            ),
            SpotifySimplifiedTrack(
                artists = listOf(artist),
                availableMarkets = listOf("AR"),
                discNumber = 1,
                durationMs = 241426,
                explicit = false,
                externalUrls = ExternalUrls(spotify = "https://open.spotify.com/track/5gXdinVZqeuDIVxogWzRk0"),
                href = "https://api.spotify.com/v1/tracks/5gXdinVZqeuDIVxogWzRk0",
                id = "5gXdinVZqeuDIVxogWzRk0",
                name = "Amsterdam",
                trackNumber = 6,
                type = "track",
                uri = "spotify:track:5gXdinVZqeuDIVxogWzRk0",
                isLocal = false
            ),
            SpotifySimplifiedTrack(
                artists = listOf(artist),
                availableMarkets = listOf("AR"),
                discNumber = 1,
                durationMs = 235386,
                explicit = false,
                externalUrls = ExternalUrls(spotify = "https://open.spotify.com/track/2OgOgEBfiZEj2XlIY2XD7f"),
                href = "https://api.spotify.com/v1/tracks/2OgOgEBfiZEj2XlIY2XD7f",
                id = "2OgOgEBfiZEj2XlIY2XD7f",
                name = "Hear Me",
                trackNumber = 7,
                type = "track",
                uri = "spotify:track:2OgOgEBfiZEj2XlIY2XD7f",
                isLocal = false
            )
        )

        // Create SpotifyAlbumTracks object
        val albumTracks = SpotifyAlbumTracks(
            href = "https://api.spotify.com/v1/albums/6nxDQi0FeEwccEPJeNySoS/tracks?offset=0&limit=50&locale=en-US,en%3Bq%3D0.5",
            limit = 50,
            next = null,
            offset = 0,
            previous = null,
            total = 14,
            items = tracks
        )

        // Create Copyright objects
        val copyrights = listOf(
            Copyright(text = "© 2013 KIDinaKORNER/Interscope Records", type = "C"),
            Copyright(text = "℗ 2013 KIDinaKORNER/Interscope Records", type = "P")
        )

        // Create ExternalIds object
        val externalIds = ExternalIds(upc = "00602537315741")

        // Create SpotifyAlbumExtended object
        val spotifyAlbumExtended = SpotifyAlbumExtended(
            albumType = "album",
            totalTracks = 14,
            availableMarkets = listOf("AR"),
            externalUrls = albumExternalUrl,
            href = "https://api.spotify.com/v1/albums/6nxDQi0FeEwccEPJeNySoS?locale=en-US%2Cen%3Bq%3D0.5",
            id = "6nxDQi0FeEwccEPJeNySoS",
            images = images,
            name = "Night Visions",
            releaseDate = "2012-09-04",
            releaseDatePrecision = "day",
            type = "album",
            uri = "spotify:album:6nxDQi0FeEwccEPJeNySoS",
            artists = listOf(artist),
            tracks = albumTracks,
            copyrights = copyrights,
            externalIds = externalIds,
            genres = listOf(),
            label = "Kid Ina Korner / Interscope",
            popularity = 84
        )

        // Create SavedAlbum object
        val album = SavedAlbum(
            addedAt = "2024-03-21T17:07:05Z",
            album = spotifyAlbumExtended
        )

        assertEquals(album.album.tracks.items[1], savedAlbums?.items?.get(0)?.album?.tracks?.items?.get(1))
    }

    @Test
    fun networkDataSource_deserializationOfSavedPlaylists_deserializesCorrectly() = runTest(testDispatcher) {

        val savedPlaylists = networkDataSource.getSavedPlaylists(0, 0)

        val response = SavedPlaylistResponse(
            href = "https://api.spotify.com/v1/users/ezs5m6sxmb82qowlqsdai2bmc/playlists?offset=0&limit=50&locale=en-US,en;q%3D0.5",
            limit = 50,
            next = null,
            offset = 0,
            previous = null,
            total = 11,
            items = listOf(
                SpotifySimplifiedPlaylist(
                    collaborative = false,
                    description = "All the songs with more than 1 BILLION streams on Spotify. Wrecking Ball, you're up next \uD83D\uDC40 !  Cover: Mark Ronson & Miley Cyrus",
                    externalUrls = ExternalUrls(
                        spotify = "https://open.spotify.com/playlist/37i9dQZF1DX7iB3RCnBnN4"
                    ),
                    href = "https://api.spotify.com/v1/playlists/37i9dQZF1DX7iB3RCnBnN4",
                    id = "37i9dQZF1DX7iB3RCnBnN4",
                    images = listOf(
                        SpotifyImage(
                            height = null,
                            url = "https://i.scdn.co/image/ab67706f000000028ed8f953b0402422623ad3ca",
                            width = null
                        )
                    ),
                    name = "BILLIONS CLUB",
                    owner = SpotifyOwner(
                        displayName = "Spotify",
                        externalUrls = ExternalUrls(
                            spotify = "https://open.spotify.com/user/spotify"
                        ),
                        href = "https://api.spotify.com/v1/users/spotify",
                        id = "spotify",
                        type = "user",
                        uri = "spotify:user:spotify"
                    ),
                    public = true,
                    snapshotId = "ZwbZzgAAAAC4uv5SjRXdla+m7JOnCMlJ",
                    tracks = SpotifyTracks(
                        href = "https://api.spotify.com/v1/playlists/37i9dQZF1DX7iB3RCnBnN4/tracks",
                        total = 762
                    ),
                    type = "playlist",
                    uri = "spotify:playlist:37i9dQZF1DX7iB3RCnBnN4",
                    primaryColor = "#ffffff"
                )
            )
        )

        assertEquals(response.items[0], savedPlaylists?.items?.get(0))
    }

    @Test
    fun networkDataSource_deserializationOfPlaylistTracks_deserializesCorrectly() = runTest(testDispatcher) {

        val playlistTracks = networkDataSource.getPlaylistTracks("", "")

        assertEquals(
            PlaylistTrack(
                addedAt = "2015-01-15T12:39:22Z",
                addedBy = AddedBy(
                    externalUrls = ExternalUrls(spotify = "https://open.spotify.com/user/jmperezperez"),
                    href = "https://api.spotify.com/v1/users/jmperezperez",
                    id = "jmperezperez",
                    type = "user",
                    uri = "spotify:user:jmperezperez"
                ),
                isLocal = false,
                primaryColor = null,
                videoThumbnail = VideoThumbnail(url = null),
                track = SpotifyTrack(
                    album = SpotifyAlbum(
                        albumType = "compilation",
                        totalTracks = 20,
                        availableMarkets = listOf("AR"),
                        externalUrls = ExternalUrls(spotify = "https://open.spotify.com/album/2pANdqPvxInB0YvcDiw4ko"),
                        href = "https://api.spotify.com/v1/albums/2pANdqPvxInB0YvcDiw4ko",
                        id = "2pANdqPvxInB0YvcDiw4ko",
                        images = listOf(
                            SpotifyImage(url = "https://i.scdn.co/image/ab67616d0000b273ce6d0eef0c1ce77e5f95bbbc", height = 640, width = 640),
                            SpotifyImage(url = "https://i.scdn.co/image/ab67616d00001e02ce6d0eef0c1ce77e5f95bbbc", height = 300, width = 300),
                            SpotifyImage(url = "https://i.scdn.co/image/ab67616d00004851ce6d0eef0c1ce77e5f95bbbc", height = 64, width = 64)
                        ),
                        name = "Progressive Psy Trance Picks Vol.8",
                        releaseDate = "2012-04-02",
                        releaseDatePrecision = "day",
                        uri = "spotify:album:2pANdqPvxInB0YvcDiw4ko",
                        artists = listOf(
                            SpotifySimplifiedArtist(
                                externalUrls = ExternalUrls(spotify = "https://open.spotify.com/artist/0LyfQWJT6nXafLPZqxe9Of"),
                                href = "https://api.spotify.com/v1/artists/0LyfQWJT6nXafLPZqxe9Of",
                                id = "0LyfQWJT6nXafLPZqxe9Of",
                                name = "Various Artists",
                                type = "artist",
                                uri = "spotify:artist:0LyfQWJT6nXafLPZqxe9Of"
                            )
                        ),
                        type = "album"
                    ),
                    artists = listOf(
                        SpotifyArtist(
                            externalUrls = ExternalUrls(spotify = "https://open.spotify.com/artist/6eSdhw46riw2OUHgMwR8B5"),
                            href = "https://api.spotify.com/v1/artists/6eSdhw46riw2OUHgMwR8B5",
                            id = "6eSdhw46riw2OUHgMwR8B5",
                            name = "Odiseo",
                            uri = "spotify:artist:6eSdhw46riw2OUHgMwR8B5",
                            type = "artist"
                        )
                    ),
                    availableMarkets = listOf("AR"),
                    discNumber = 1,
                    durationMs = 376000,
                    explicit = false,
                    externalIds = ExternalIds(isrc = "DEKC41200989"),
                    externalUrls = ExternalUrls(spotify = "https://open.spotify.com/track/4rzfv0JLZfVhOhbSQ8o5jZ"),
                    href = "https://api.spotify.com/v1/tracks/4rzfv0JLZfVhOhbSQ8o5jZ",
                    id = "4rzfv0JLZfVhOhbSQ8o5jZ",
                    name = "Api",
                    popularity = 1,
                    previewUrl = "https://p.scdn.co/mp3-preview/04599a1fe12ffac01d2bcb08340f84c0dd2cc335?cid=cfe923b2d660439caf2b557b21f31221",
                    trackNumber = 10,
                    type = "track",
                    uri = "spotify:track:4rzfv0JLZfVhOhbSQ8o5jZ",
                    isLocal = false,
                    track = true,
                    episode = false
                )
            ),
            playlistTracks[0]
        )
    }

    @Test
    fun networkDataSource_deserializationOfTrack_deserializesCorrectly() = runTest(testDispatcher) {

        val track = networkDataSource.getTrack("")

        assertEquals(
            SpotifyTrack(
                album = SpotifyAlbum(
                    albumType = "album",
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
                popularity = 72,
                previewUrl = "https://p.scdn.co/mp3-preview/a5a49157caae8d8fbb5c82b70a6244a9ec828562?cid=cfe923b2d660439caf2b557b21f31221",
                trackNumber = 11,
                type = "track",
                uri = "spotify:track:0ZfByLXCeKchuj7zi1CJ0S"
            ),
            track
        )
    }
}