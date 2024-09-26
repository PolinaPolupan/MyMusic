package com.example.data.repository

import com.example.database.MusicDao
import com.example.database.model.crossRef.AlbumArtistCrossRef
import com.example.database.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.database.model.crossRef.TrackArtistCrossRef
import com.example.network.model.SpotifyTrack
import com.example.network.model.toLocal
import com.example.network.model.toLocalSimplified
import com.example.network.model.toLocalSimplifiedTrack
import com.example.network.model.toLocalTrack

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