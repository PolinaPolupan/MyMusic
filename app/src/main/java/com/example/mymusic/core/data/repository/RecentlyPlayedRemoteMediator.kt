package com.example.mymusic.core.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.local.model.LocalRecentlyPlayedWithArtists
import com.example.mymusic.core.data.local.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.SimplifiedTrackArtistCrossRef
import com.example.mymusic.core.data.local.model.crossRef.TrackArtistCrossRef
import com.example.mymusic.core.data.local.model.entities.RemoteKeys
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.RecentlyPlayedTracksResponse
import com.example.mymusic.core.data.network.model.SpotifyTrack
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalSimplified
import com.example.mymusic.core.data.network.model.toLocalSimplifiedTrack
import com.example.mymusic.core.data.network.model.toLocalTrack
import com.haroldadmin.cnradapter.NetworkResponse
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecentlyPlayedRemoteMediator @Inject constructor(
    private val apiService: MyMusicAPIService,
    private val musicDatabase: MusicDatabase
): RemoteMediator<Int, LocalRecentlyPlayedWithArtists>() {

    private val musicDao = musicDatabase.musicDao()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalRecentlyPlayedWithArtists>
    ): MediatorResult {
        val time = System.currentTimeMillis().toString()
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.prevKey ?: time
            }
            LoadType.PREPEND -> {
                // Don't load data at the beginning since we are loading history from the current time
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
        }

        try {
            val response = apiService.getRecentlyPlayed(before = page.toString())
            val items = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body?.items
            val data = (response as? NetworkResponse.Success<RecentlyPlayedTracksResponse, ErrorResponse>?)?.body

            val recentlyPlayed = processResponse(response, items, emptyList()) ?: emptyList()
            val cursors = processResponse(response, data, null)?.cursors

            val nextKey = cursors?.after?.toLong()
            val prevKey = cursors?.before?.toLong()

            val endOfPaginationReached = recentlyPlayed.isEmpty()

            musicDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    musicDatabase.remoteKeysDao().clearRemoteKeys()
                    musicDao.deleteRecentlyPlayed()
                }

                val keys = recentlyPlayed.map {
                    RemoteKeys(id = it.track.id, prevKey = prevKey, nextKey = nextKey)
                }
                musicDatabase.remoteKeysDao().insertAll(keys)

                for (track in recentlyPlayed) {
                    upsertTrack(track.track)
                }

                musicDao.upsertLocalPlayHistory(recentlyPlayed.toLocal())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalRecentlyPlayedWithArtists>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { track ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeysRepoId(track.trackHistory.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalRecentlyPlayedWithArtists>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.trackHistory?.id?.let { id ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeysRepoId(id)
            }
        }
    }

    private suspend fun upsertTrack(track: SpotifyTrack) {
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
}