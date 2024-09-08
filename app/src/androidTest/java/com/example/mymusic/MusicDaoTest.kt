package com.example.mymusic

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.local.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.entities.toExternal
import com.example.mymusic.core.data.local.model.entities.toExternalSimplified
import com.example.mymusic.core.data.local.model.toExternal
import com.example.network.model.ExternalUrls
import com.example.network.model.SavedAlbum
import com.example.network.model.SpotifyAlbum
import com.example.network.model.SpotifyArtist
import com.example.network.model.SpotifyOwner
import com.example.network.model.SpotifyPlayHistoryObject
import com.example.network.model.SpotifySimplifiedArtist
import com.example.network.model.SpotifySimplifiedPlaylist
import com.example.network.model.SpotifyTrack
import com.example.network.model.SpotifyTracks
import com.example.network.model.toLocal
import com.example.network.model.toLocalAlbum
import com.example.network.model.toLocalRecommendations
import com.example.network.model.toLocalSaved
import com.example.network.model.toLocalSimplified
import com.example.network.model.toLocalSimplifiedTrack
import com.example.network.model.toLocalSimplifiedTracks
import com.example.network.model.toLocalTrack
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MusicDaoTest {

    private lateinit var musicDao: MusicDao
    private lateinit var db: MusicDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MusicDatabase::class.java,
        ).build()
        musicDao = db.musicDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun musicDao_upsertTrack() = runTest {

        // Initialize data
        val networkArtists = listOf(testNetworkArtist("0", "Imagine Dragons"))
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Dua Lipa"))
        val networkAlbum = testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists)
        val networkTrack = testNetworkTrack("0", "New Rules", networkAlbum, networkArtists)

        // Upsert to the database
        upsertTrack(networkTrack)

        val savedTrack = musicDao.observeTrack("0").first()

        // Map to external
        val simplifiedArtists = networkSimplifiedArtists.toLocal().toExternal()
        val artists = networkArtists.toLocal().toExternal()
        val track = networkTrack.toLocalTrack().toExternal(simplifiedArtists, artists)


        assertEquals(savedTrack.toExternal(), track)
    }

    @Test
    fun musicDao_upsertRecommendations() = runTest {

        // Initialize data
        val networkArtists = listOf(testNetworkArtist("0", "Dua Lipa"))
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("1", "Dua Lipa"))
        val networkAlbum = testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists)
        val networkTracks = listOf(
            testNetworkTrack("0", "New Rules", networkAlbum, networkArtists),
            testNetworkTrack("1", "Genesis", networkAlbum, networkArtists),
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists))

        // Upsert tracks to the database
        for (track in networkTracks) {
            upsertTrack(track)
        }

        // Upsert tracks ids to the recommendations
        musicDao.upsertRecommendations(networkTracks.toLocalRecommendations())

        // Map to external
        val savedRecommendations = musicDao.observeRecommendations().first().toExternal()

        val simplifiedArtists = networkSimplifiedArtists.toLocal().toExternal()
        val artists = networkArtists.toLocal().toExternal()

        val recommendations = mutableListOf<com.example.model.Track>()
        for (track in networkTracks) {
            recommendations.add(track.toLocalTrack().toExternal(simplifiedArtists, artists))
        }


        assertEquals(recommendations, savedRecommendations)
    }

    @Test
    fun musicDao_upsertRecentlyPlayed() = runTest {

        // Initialize data
        val networkArtists = listOf(testNetworkArtist("1", "Dua Lipa"))
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Rhianna"))
        val networkAlbum = testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists)
        val networkTracks = listOf(
            testNetworkTrack("0", "New Rules", networkAlbum, networkArtists),
            testNetworkTrack("1", "Genesis", networkAlbum, networkArtists),
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists))
        val context = com.example.network.model.Context(
            type = "",
            href = "",
            externalUrls = com.example.network.model.ExternalUrls(""),
            uri = ""
        )
        val recentlyPlayed = listOf(
            com.example.network.model.SpotifyPlayHistoryObject(
                networkTracks[0],
                playedAt = null,
                context = context
            ),
            com.example.network.model.SpotifyPlayHistoryObject(
                networkTracks[1],
                playedAt = null,
                context = context
            ),
            com.example.network.model.SpotifyPlayHistoryObject(
                networkTracks[2],
                playedAt = null,
                context = context
            )
        )

        // Upsert tracks to the database
        for (track in networkTracks) {
            upsertTrack(track)
        }

        musicDao.upsertLocalPlayHistory(recentlyPlayed.toLocal())

        // Map to external
//        val derivedRecentlyPlayed = musicDao.observeRecentlyPlayed().first().toExternal()
//
//        val simplifiedArtists = networkSimplifiedArtists.toLocal().toExternal()
//        val artists = networkArtists.toLocal().toExternal()
//        val recentlyPlayedTracks = networkTracks.toLocal().map { it.toExternal(simplifiedArtists, artists) }
//
//
//        assertEquals(derivedRecentlyPlayed, recentlyPlayedTracks)
    }

    @Test
    fun musicDao_upsertSavedAlbums() = runTest {

        // Initialize data
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Dua Lipa"))
        val networkAlbums = listOf(
            testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists),
            testNetworkAlbum("1", "V", networkSimplifiedArtists),
            testNetworkAlbum("2", "1989", networkSimplifiedArtists)
        )
        val networkSavedAlbums = listOf(
            com.example.network.model.SavedAlbum("", networkAlbums[0]),
            com.example.network.model.SavedAlbum("", networkAlbums[1]),
            com.example.network.model.SavedAlbum("", networkAlbums[2])
        )

        // Upsert to the database
        musicDao.upsertSavedAlbums(networkSavedAlbums.toLocal())
        musicDao.upsertAlbums(networkSavedAlbums.toLocalAlbum())

        for (album in networkSavedAlbums) {
            val tempAlbum = album.album
            musicDao.upsertSimplifiedArtists(tempAlbum.artists.toLocal())
            for (artist in tempAlbum.artists)
                musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(albumId = tempAlbum.id, simplifiedArtistId = artist.id))
        }

        // Map to external
        val albums = networkSavedAlbums.toLocalAlbum().map { it.toExternalSimplified(networkSimplifiedArtists.toLocal().toExternal()) }
//        val derivedAlbums = musicDao.observeSavedAlbums().first().toExternal()
//
//
//        assertEquals(albums, derivedAlbums)
    }

    @Test
    fun musicDao_upsertAlbumTracks() = runTest {

        // Initialize data
        val networkArtists = listOf(testNetworkArtist("0", "Dua Lipa"))
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Dua Lipa"))
        val networkAlbum = testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists)
        val networkTracks = listOf(
            testNetworkTrack("0", "New Rules", networkAlbum, networkArtists),
            testNetworkTrack("1", "Genesis", networkAlbum, networkArtists),
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists))

        // Upsert tracks
        musicDao.upsertSimplifiedTracks(networkTracks.toLocalSimplifiedTracks())

        for (track in networkTracks) {
            musicDao.upsertSimplifiedArtists(track.artists.toLocalSimplified())
            musicDao.upsertAlbumTrackCrossRef(AlbumTrackCrossRef(track.id, networkAlbum.id))
            for (artist in track.artists)
                musicDao.upsertSimplifiedTrackArtistCrossRef(SimplifiedTrackArtistCrossRef(track.id, artist.id))
        }

        // Map to external
        val albumTracks = musicDao.observeAlbumTracks("0").first().toExternal()
        val simplifiedTracks = networkTracks.map {
            com.example.model.SimplifiedTrack(
                it.id,
                it.name,
                networkSimplifiedArtists.toLocal().toExternal()
            )
        }


        assertEquals(albumTracks, simplifiedTracks)
    }

    @Test
    fun musicDao_upsertPlaylistTracks() = runTest {

        // Initialize data
        val networkArtists = listOf(testNetworkArtist("0", "Dua Lipa"))
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Dua Lipa"))
        val networkAlbum = testNetworkAlbum("0", "Dua Lipa", networkSimplifiedArtists)
        val networkTracks = listOf(
            testNetworkTrack("0", "New Rules", networkAlbum, networkArtists),
            testNetworkTrack("1", "Genesis", networkAlbum, networkArtists),
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists))
        val spotifyTrack = com.example.network.model.SpotifyTracks("", 0)
        val playlist = networkPlaylist("0", "Playlist", spotifyTrack)

        // Upsert tracks
        for (track in networkTracks) {
            musicDao.upsertPlaylistTrackCrossRef(PlaylistTrackCrossRef(playlist.id, track.id))
            upsertTrack(track)
        }

        // Map to external
        val playlistTracks = musicDao.observePlaylistTracks("0").first().toExternal()
        val tracks = networkTracks.toLocal().map { it.toExternal(networkSimplifiedArtists.toLocal().toExternal(), networkArtists.toLocal().toExternal()) }


        assertEquals(playlistTracks, tracks)
    }

    @Test
    fun musicDao_upsertSavedPlaylists() = runTest {

        // Initialize data
        val spotifyTrack = com.example.network.model.SpotifyTracks("", 0)
        val networkPlaylists = listOf(
            networkPlaylist("0", "Playlist", spotifyTrack),
            networkPlaylist("1", "Playlist1", spotifyTrack),
            networkPlaylist("2", "Playlist2", spotifyTrack),
        )

        // Upsert playlists
        musicDao.upsertPlaylists(networkPlaylists.toLocal())
        musicDao.upsertSavedPlaylists(networkPlaylists.toLocalSaved())


    }

    private suspend fun upsertTrack(track: com.example.network.model.SpotifyTrack) {

        musicDao.upsertTrack(track.toLocalTrack())
        musicDao.upsertSimplifiedTrack(track.toLocalSimplifiedTrack())
        for (artist in track.artists) {
            musicDao.upsertTrackArtistCrossRef(TrackArtistCrossRef(artist.id, track.id))
            musicDao.upsertSimplifiedTrackArtistCrossRef(SimplifiedTrackArtistCrossRef(artist.id, track.id))
        }

        val album = track.album
        musicDao.upsertAlbum(album.toLocal())
        musicDao.upsertArtists(track.artists.toLocal())
        musicDao.upsertSimplifiedArtists(track.artists.toLocalSimplified())
        musicDao.upsertSimplifiedArtists(album.artists.toLocal())
        for (artist in album.artists)
            musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(artist.id, album.id))
    }

    private fun testNetworkArtist(
        id: String,
        name: String
    ) = com.example.network.model.SpotifyArtist(
        externalUrls = null,
        followers = null,
        genres = null,
        href = "",
        id = id,
        images = null,
        name = name,
        popularity = null,
        type = "",
        uri = ""
    )

    private fun testNetworkSimplifiedArtist(
        id: String,
        name: String
    ) = com.example.network.model.SpotifySimplifiedArtist(
        externalUrls = null,
        href = "",
        id = id,
        type = "",
        uri = "",
        name = name
    )

    private fun testNetworkAlbum(
        id: String,
        name: String,
        simplifiedArtists: List<com.example.network.model.SpotifySimplifiedArtist>
    ) = com.example.network.model.SpotifyAlbum(
        albumType = null,
        totalTracks = 1,
        availableMarkets = null,
        externalUrls = null,
        href = "",
        id = id,
        images = emptyList(),
        name = name,
        releaseDate = null,
        releaseDatePrecision = null,
        restrictions = null,
        type = "album",
        uri = "",
        artists = simplifiedArtists
    )

    private fun testNetworkTrack(
        id: String,
        name: String,
        album: com.example.network.model.SpotifyAlbum,
        artists: List<com.example.network.model.SpotifyArtist>
    ) = com.example.network.model.SpotifyTrack(
        album = album,
        artists = artists,
        availableMarkets = null,
        discNumber = 0,
        durationMs = 0,
        explicit = false,
        externalIds = null,
        externalUrls = null,
        href = "",
        id = id,
        isPlayable = null,
        linkedFrom = null,
        restrictions = null,
        name = name,
        popularity = 0,
        previewUrl = null,
        trackNumber = 1,
        type = "",
        uri = "",
        isLocal = false
    )

    private fun networkPlaylist(
        id: String,
        name: String,
        tracks: com.example.network.model.SpotifyTracks
    ) = com.example.network.model.SpotifySimplifiedPlaylist(
        collaborative = true,
        description = "",
        externalUrls = null,
        href = "",
        id = id,
        images = null,
        name = name,
        owner = com.example.network.model.SpotifyOwner(
            externalUrls = null,
            followers = null,
            href = "",
            type = "",
            uri = "",
            displayName = null,
            id = "0"
        ),
        public = false,
        snapshotId = null,
        tracks = tracks,
        type = "playlist",
        uri = ""
    )
}