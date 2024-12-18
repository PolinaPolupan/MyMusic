package com.example.mymusic.core.database

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.AlbumTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.PlaylistTrackCrossRef
import com.example.mymusic.core.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.database.model.entities.toExternal
import com.example.mymusic.core.database.model.entities.toExternalSimplified
import com.example.mymusic.core.database.model.toExternal
import com.example.mymusic.core.database.model.toExternalSimplified
import com.example.mymusic.core.model.SimplifiedTrack
import com.example.mymusic.core.model.Track
import com.example.mymusic.core.network.model.ExternalIds
import com.example.mymusic.core.network.model.ExternalUrls
import com.example.mymusic.core.network.model.SavedAlbum
import com.example.mymusic.core.network.model.SpotifyAlbum
import com.example.mymusic.core.network.model.SpotifyAlbumExtended
import com.example.mymusic.core.network.model.SpotifyAlbumTracks
import com.example.mymusic.core.network.model.SpotifyArtist
import com.example.mymusic.core.network.model.SpotifyOwner
import com.example.mymusic.core.network.model.SpotifyPlayHistoryObject
import com.example.mymusic.core.network.model.SpotifySimplifiedArtist
import com.example.mymusic.core.network.model.SpotifySimplifiedPlaylist
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.SpotifyTracks
import com.example.mymusic.core.network.model.toLocal
import com.example.mymusic.core.network.model.toLocalAlbum
import com.example.mymusic.core.network.model.toLocalRecommendations
import com.example.mymusic.core.network.model.toLocalSimplified
import com.example.mymusic.core.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.network.model.toLocalSimplifiedTracks
import com.example.mymusic.core.network.model.toLocalTrack
import com.example.mymusic.core.network.model.toLocalTracks
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
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists)
        )

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

        val recommendations = mutableListOf<Track>()
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
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists)
        )
        val context = com.example.mymusic.core.network.model.Context(
            type = "",
            href = "",
            externalUrls = ExternalUrls(""),
            uri = ""
        )
        val recentlyPlayed = listOf(
            SpotifyPlayHistoryObject(
                networkTracks[0],
                playedAt = null,
                context = context
            ),
            SpotifyPlayHistoryObject(
                networkTracks[1],
                playedAt = null,
                context = context
            ),
            SpotifyPlayHistoryObject(
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
         val derivedRecentlyPlayed = musicDao.observeRecentlyPlayed().load(PagingSource.LoadParams.Refresh(0, 10,  true))

         val simplifiedArtists = networkSimplifiedArtists.toLocal().toExternal()
         val artists = networkArtists.toLocal().toExternal()
         val recentlyPlayedTracks = networkTracks.toLocalTracks().map { it.toExternal(simplifiedArtists, artists) }

         val actual = (derivedRecentlyPlayed as? PagingSource.LoadResult.Page)?.data

         assertEquals(actual?.toExternal(), recentlyPlayedTracks)
    }

    @Test
    fun musicDao_upsertSavedAlbums() = runTest {

        // Initialize data
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("0", "Dua Lipa"))
        val networkAlbums = listOf(
            testNetworkAlbumExtended("0", "Dua Lipa", networkSimplifiedArtists),
            testNetworkAlbumExtended("1", "V", networkSimplifiedArtists),
            testNetworkAlbumExtended("2", "1989", networkSimplifiedArtists)
        )
        val networkSavedAlbums = listOf(
            SavedAlbum("", networkAlbums[0]),
            SavedAlbum("", networkAlbums[1]),
            SavedAlbum("", networkAlbums[2])
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

        val derivedAlbums = musicDao.observeSavedAlbums().load(PagingSource.LoadParams.Refresh(0, 10,  true))

        val actual = (derivedAlbums as? PagingSource.LoadResult.Page)?.data

        assertEquals(actual?.toExternalSimplified(), albums)
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
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists)
        )

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
            SimplifiedTrack(
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
        val networkSimplifiedArtists = listOf(testNetworkSimplifiedArtist("1", "Taylor Swift"))
        val networkAlbum = testNetworkAlbum("0", "Maroon", networkSimplifiedArtists)
        val networkTracks = listOf(
            testNetworkTrack("0", "New Rules", networkAlbum, networkArtists),
            testNetworkTrack("1", "Genesis", networkAlbum, networkArtists),
            testNetworkTrack("2", "Be The One", networkAlbum, networkArtists)
        )
        val spotifyTrack = SpotifyTracks("", 0)
        val playlist = networkPlaylist("0", "Playlist", spotifyTrack)

        // Upsert tracks
        for (track in networkTracks) {
            musicDao.upsertPlaylistTrackCrossRef(PlaylistTrackCrossRef(playlist.id, track.id))
            upsertTrack(track)
        }

        // Map to external
        val playlistTracks = musicDao.observePlaylistTracks("0").first().toExternal()
        val derivedTracks = networkTracks.map { it.toLocalTrack().toExternal(networkSimplifiedArtists.toLocal().toExternal(), networkArtists.toLocal().toExternal()) }

        assertEquals(playlistTracks, derivedTracks)
    }

    /**
     * [upsertTrack] utility function. Upserts SpotifyTrack into the database
     */
    private suspend fun upsertTrack(track: SpotifyTrack) {

        // Save two versions of the track - local track and simplified
        musicDao.upsertTrack(track.toLocalTrack())
        musicDao.upsertSimplifiedTrack(track.toLocalSimplifiedTrack())

        // Upsert the cross references between track and artists (many-to-many relationship)
        for (artist in track.artists) {
            musicDao.upsertTrackArtistCrossRef(TrackArtistCrossRef(artist.id, track.id))
            musicDao.upsertSimplifiedTrackArtistCrossRef(SimplifiedTrackArtistCrossRef(artist.id, track.id))
        }

        // Upsert album and it's artists
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
    ) = SpotifyArtist(
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
    ) = SpotifySimplifiedArtist(
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
        simplifiedArtists: List<SpotifySimplifiedArtist>
    ) = SpotifyAlbum(
        albumType = null,
        totalTracks = 1,
        availableMarkets = null,
        externalUrls = null,
        href= "",
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

    private fun testNetworkAlbumExtended(
        id: String,
        name: String,
        simplifiedArtists: List<SpotifySimplifiedArtist>
    ) = SpotifyAlbumExtended(
        albumType = null,
        totalTracks = 1,
        availableMarkets = null,
        externalUrls = null,
        href= "",
        id = id,
        images = emptyList(),
        name = name,
        releaseDate = null,
        releaseDatePrecision = null,
        restrictions = null,
        type = "album",
        uri = "",
        artists = simplifiedArtists,
        copyrights = emptyList(),
        externalIds = ExternalIds(),
        genres = emptyList(),
        label = "",
        popularity = 0,
        tracks = SpotifyAlbumTracks(
            href = "",
            limit = 0,
            items = emptyList(),
            next = "",
            offset = 0,
            previous = "",
            total = 0
        )
    )

    private fun testNetworkTrack(
        id: String,
        name: String,
        album: SpotifyAlbum,
        artists: List<SpotifyArtist>
    ) = SpotifyTrack(
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
        tracks: SpotifyTracks
    ) = SpotifySimplifiedPlaylist(
        collaborative = true,
        description = "",
        externalUrls = null,
        href = "",
        id = id,
        images = null,
        name = name,
        owner = SpotifyOwner(
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
        uri = "",
        primaryColor = null
    )
}


