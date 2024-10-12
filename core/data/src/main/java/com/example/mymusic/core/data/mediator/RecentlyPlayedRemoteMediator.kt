package com.example.mymusic.core.data.mediator

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymusic.core.database.model.LocalRecentlyPlayedWithArtists
import com.example.mymusic.core.data.repository.upsertTrack
import com.example.mymusic.core.database.MusicDatabase
import com.example.mymusic.core.database.model.entities.CursorRemoteKeys
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.model.toLocal
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RecentlyPlayedRemoteMediator @Inject constructor(
    private val networkDataSource: MyMusicNetworkDataSource,
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
                remoteKeys?.before ?: time
            }
            LoadType.PREPEND -> {
                // Don't load data at the beginning since we are loading history from the current time
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.before
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
        }

        try {
            val data = networkDataSource.getRecentlyPlayed(before = page.toString())
            val cursors = data?.cursors
            val recentlyPlayed = data?.items

            val nextKey = cursors?.after?.toLong()
            val prevKey = cursors?.before?.toLong()

            val endOfPaginationReached = recentlyPlayed?.isEmpty() ?: true

            musicDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    musicDatabase.remoteKeysDao().clearRemoteCursors()
                    musicDao.deleteRecentlyPlayed()
                }

                val keys = recentlyPlayed?.map {
                    CursorRemoteKeys(id = it.track.id, before = prevKey, after = nextKey)
                }
                if (keys != null) {
                    musicDatabase.remoteKeysDao().insertAllCursors(keys)
                }

                if (recentlyPlayed != null) {
                    for (track in recentlyPlayed) {
                        upsertTrack(track.track, musicDao)
                    }
                }

                if (recentlyPlayed != null) {
                    musicDao.upsertLocalPlayHistory(recentlyPlayed.toLocal())
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalRecentlyPlayedWithArtists>): CursorRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { track ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeysCursors(track.trackHistory.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocalRecentlyPlayedWithArtists>
    ): CursorRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.trackHistory?.id?.let { id ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeysCursors(id)
            }
        }
    }
}