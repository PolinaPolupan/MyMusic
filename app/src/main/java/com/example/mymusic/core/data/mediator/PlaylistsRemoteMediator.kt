package com.example.mymusic.core.data.mediator

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymusic.core.data.local.MusicDatabase
import com.example.mymusic.core.data.local.model.entities.LocalPlaylist
import com.example.mymusic.core.data.local.model.entities.RemoteKeys
import com.example.mymusic.core.data.network.MyMusicAPIService
import com.example.mymusic.core.data.network.model.ErrorResponse
import com.example.mymusic.core.data.network.model.SavedPlaylistResponse
import com.example.mymusic.core.data.network.model.toLocal
import com.example.mymusic.core.data.network.model.toLocalSaved
import com.example.mymusic.core.data.repository.processResponse
import com.haroldadmin.cnradapter.NetworkResponse
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PlaylistsRemoteMediator(
    private val apiService: MyMusicAPIService,
    private val musicDatabase: MusicDatabase
): RemoteMediator<Int, LocalPlaylist>()  {

    private val musicDao = musicDatabase.musicDao()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalPlaylist>
    ): MediatorResult {
        val limit = state.config.pageSize

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(limit) ?: 0
            }
            LoadType.PREPEND -> {
                // Don't load data before the first page
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = apiService.getSavedPlaylists(offset = page, limit = limit)

            val data = (apiResponse as? NetworkResponse.Success<SavedPlaylistResponse, ErrorResponse>?)?.body?.items ?: emptyList()
            val next = (apiResponse as? NetworkResponse.Success<SavedPlaylistResponse, ErrorResponse>?)?.body?.next
            val playlists =  processResponse(apiResponse, data, emptyList())

            val endOfPaginationReached = playlists.isEmpty() || next == null

            musicDatabase.withTransaction {

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    musicDatabase.remoteKeysDao().clearRemoteKeys()
                    musicDao.deleteSavedPlaylists()
                }

                val prevKey = if (page == 0) null else page - limit
                val nextKey = if (endOfPaginationReached) null else page + limit

                val keys = playlists.map {
                    RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                musicDatabase.remoteKeysDao().insertAllKeys(keys)

                if (playlists.isNotEmpty()) {
                    musicDao.deleteSavedPlaylists()
                    musicDao.upsertPlaylists(playlists.toLocal())
                    musicDao.upsertSavedPlaylists(playlists.toLocalSaved())
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalPlaylist>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { playlist ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeys(playlist.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, LocalPlaylist>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                musicDatabase.remoteKeysDao().remoteKeys(id)
            }
        }
    }
}