package com.example.mymusic.core.data.mediator

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mymusic.core.database.model.LocalAlbumWithArtists
import com.example.mymusic.core.database.MusicDatabase
import com.example.mymusic.core.database.model.crossRef.AlbumArtistCrossRef
import com.example.mymusic.core.database.model.entities.RemoteKeys
import com.example.mymusic.core.network.MyMusicNetworkDataSource
import com.example.mymusic.core.network.model.toLocal
import com.example.mymusic.core.network.model.toLocalAlbum
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AlbumsRemoteMediator @Inject constructor(
    private val networkDataSource: MyMusicNetworkDataSource,
    private val musicDatabase: MusicDatabase
): RemoteMediator<Int, LocalAlbumWithArtists>() {

    private val musicDao = musicDatabase.musicDao()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalAlbumWithArtists>
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
            val data = networkDataSource.getSavedAlbums(offset = page, limit = limit)

            val next = data?.next
            val albums = data?.items

            val endOfPaginationReached = albums?.isEmpty() == true

            musicDatabase.withTransaction {

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    musicDatabase.remoteKeysDao().clearRemoteKeys()
                    musicDao.deleteSavedAlbums()
                }

                val prevKey = if (page == 0) null else page - limit
                val nextKey = if (endOfPaginationReached) null else page + limit

                val keys = albums?.map {
                    RemoteKeys(id = it.album.id, prevKey = prevKey, nextKey = nextKey)
                }

                if (keys != null) {
                    musicDatabase.remoteKeysDao().insertAllKeys(keys)
                }

                if (!albums.isNullOrEmpty()) {

                    musicDao.upsertSavedAlbums(albums.toLocal())
                    musicDao.upsertAlbums(albums.toLocalAlbum())

                    for (savedAlbum in albums) {
                        musicDao.upsertSimplifiedArtists(savedAlbum.album.artists.toLocal())
                        for (artist in savedAlbum.album.artists)
                            musicDao.upsertAlbumArtistCrossRef(AlbumArtistCrossRef(albumId = savedAlbum.album.id, simplifiedArtistId = artist.id))
                    }
                }
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocalAlbumWithArtists>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { album ->
                // Get the remote keys of the last item retrieved
                musicDatabase.remoteKeysDao().remoteKeys(album.album.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, LocalAlbumWithArtists>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.album?.id?.let { id ->
                musicDatabase.remoteKeysDao().remoteKeys(id)
            }
        }
    }
}