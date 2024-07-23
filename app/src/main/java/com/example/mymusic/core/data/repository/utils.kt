package com.example.mymusic.core.data.repository

import android.util.Log
import com.example.mymusic.core.data.local.MusicDao
import com.example.mymusic.core.data.local.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalSimplified
import com.example.mymusic.core.data.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.data.network.model.toLocalTrack
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * [processResponse] utility function. Handles and logs errors and returns the given data
 *
 * @param response parameter type of NetworkResponse. Contains successful and error response data
 * @param successData data which should be returned if the response is successful
 * @param errorData data which should be returned if the response has failed
 */
fun <S, E, T> processResponse(response: NetworkResponse<S, E>, successData: T, errorData: T): T {
    return when (response) {
        is  NetworkResponse.Success -> {
            Log.d("MainActivity", "Request is successful: ${response.body.toString()}")
            successData
        }

        is NetworkResponse.NetworkError -> {
            Log.e("MainActivity", "Request failed ${response.error.message ?: "Network Error"}")
            errorData
        }

        is NetworkResponse.ServerError -> {
            Log.e("MainActivity", "Request failed. Code: ${response.code.toString()} ${response.error?.message ?: "Server Error"}")
            errorData
        }

        is NetworkResponse.UnknownError -> {
            Log.e("MainActivity", "Request failed. Code: ${response.code.toString()} ${response.error.message ?: "Unknown Error"}")
            errorData
        }
    }
}

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