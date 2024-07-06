package com.example.mymusic

import com.example.mymusic.core.data.network.model.SpotifyAlbum
import com.example.mymusic.core.data.network.model.SpotifyArtist
import com.example.mymusic.core.data.network.model.SpotifyImage
import com.example.mymusic.core.data.network.model.SpotifyOwner
import com.example.mymusic.core.data.network.model.SpotifySimplifiedArtist
import com.example.mymusic.core.data.network.model.SpotifySimplifiedPlaylist
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.SpotifyTracks
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalRecommendation
import com.example.mymusic.core.data.network.model.toLocalSaved
import com.example.mymusic.core.data.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.data.network.model.toLocalTrack
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * [NetworkLocalTest] class tests data mappings from network to local models
 */
class NetworkLocalTest {

    @Test
    fun networkSimplifiedArtist_canBeMappedToLocal() {
        val networkSimplifiedArtist = SpotifySimplifiedArtist(
            externalUrls = null,
            href = "",
            id = "0",
            type = "",
            uri = "",
            name = "Dua Lipa"
        )
        val local = networkSimplifiedArtist.toLocal()

        assertEquals(networkSimplifiedArtist.id, local.id)
        assertEquals(networkSimplifiedArtist.name, local.name)
    }

    @Test
    fun networkArtist_canBeMappedToLocal() {
        val networkArtist = SpotifyArtist(
            externalUrls = null,
            href = "",
            id = "0",
            type = "",
            uri = "",
            name = "Dua Lipa",
            images = listOf(SpotifyImage("url", 0, 0))
        )
        val local = networkArtist.toLocal()

        assertEquals(networkArtist.id, local.id)
        assertEquals(networkArtist.name, local.name)
        assertEquals(networkArtist.images!![0].url, local.imageUrl)
    }

    @Test
    fun networkAlbum_canBeMappedToLocal() {
        val networkAlbum = SpotifyAlbum(
            albumType = null,
            totalTracks = 1,
            availableMarkets = null,
            externalUrls = null,
            href= "",
            id = "0",
            images = listOf(SpotifyImage("url", 0, 0)),
            name = "Album",
            releaseDate = null,
            releaseDatePrecision = null,
            restrictions = null,
            type = "album",
            uri = "",
            artists = listOf(
                SpotifySimplifiedArtist(
                    externalUrls = null,
                    href = "",
                    id = "0",
                    type = "",
                    uri = "",
                    name = "Dua Lipa"
                )
            )
        )
        val local = networkAlbum.toLocal()

        assertEquals(local.id, networkAlbum.id)
        assertEquals(local.name, networkAlbum.name)
        assertEquals(local.type, networkAlbum.type)
        assertEquals(local.imageUrl, networkAlbum.images[0].url)
    }

    @Test
    fun networkTrack_canBeMappedToLocal() {
        val networkArtists = listOf(SpotifyArtist(
            externalUrls = null,
            href = "",
            id = "0",
            type = "",
            uri = "",
            name = "Dua Lipa",
            images = listOf(SpotifyImage("url", 0, 0))
        ))
        val networkAlbum = SpotifyAlbum(
            albumType = null,
            totalTracks = 1,
            availableMarkets = null,
            externalUrls = null,
            href= "",
            id = "0",
            images = listOf(SpotifyImage("url", 0, 0)),
            name = "Album",
            releaseDate = null,
            releaseDatePrecision = null,
            restrictions = null,
            type = "album",
            uri = "",
            artists = listOf(
                SpotifySimplifiedArtist(
                    externalUrls = null,
                    href = "",
                    id = "0",
                    type = "",
                    uri = "",
                    name = "Dua Lipa"
                )
            )
        )
        val networkTrack = SpotifyTrack(
            album = networkAlbum,
            artists = networkArtists,
            availableMarkets = null,
            discNumber = 0,
            durationMs = 0,
            explicit = false,
            externalIds = null,
            externalUrls = null,
            href = "",
            id = "0",
            isPlayable = null,
            linkedFrom = null,
            restrictions = null,
            name = "Track",
            popularity = 0,
            previewUrl = null,
            trackNumber = 1,
            type = "",
            uri = "",
            isLocal = false
        )
        val local = networkTrack.toLocalTrack()

        assertEquals(local.id, networkTrack.id)
        assertEquals(local.name, networkTrack.name)
        assertEquals(local.album, networkTrack.album.toLocal())

        val localSimplified = networkTrack.toLocalSimplifiedTrack()

        assertEquals(localSimplified.id, networkTrack.id)
        assertEquals(localSimplified.name, networkTrack.name)

        val localRecommendation = networkTrack.toLocalRecommendation()

        assertEquals(localRecommendation.id, networkTrack.id)
    }

    @Test
    fun networkPlaylist_canBeMappedToLocal() {
        val networkPlaylist = SpotifySimplifiedPlaylist(
            collaborative = true,
            description = "",
            externalUrls = null,
            href = "",
            id = "0",
            images = listOf(SpotifyImage("url", 0, 0)),
            name = "Playlist",
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
            tracks = SpotifyTracks("", 0),
            type = "playlist",
            uri = ""
        )
        val local = networkPlaylist.toLocal()

        assertEquals(local.id, networkPlaylist.id)
        assertEquals(local.name, networkPlaylist.name)
        assertEquals(local.imageUrl, networkPlaylist.images!![0].url )

        val localSaved = networkPlaylist.toLocalSaved()

        assertEquals(localSaved.id, networkPlaylist.id)
    }
}