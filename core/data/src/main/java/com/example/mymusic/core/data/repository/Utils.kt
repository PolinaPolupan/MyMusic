package com.example.mymusic.core.data.repository

import com.example.mymusic.core.database.MusicDao
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.database.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.network.model.SpotifyTrack
import com.example.mymusic.core.network.model.toLocal
import com.example.mymusic.core.network.model.toLocalSimplified
import com.example.mymusic.core.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.network.model.toLocalTrack

/**
 * [upsertTrack] utility function. Upserts SpotifyTrack into the database
 */
suspend fun upsertTrack(track: SpotifyTrack, musicDao: MusicDao) {

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