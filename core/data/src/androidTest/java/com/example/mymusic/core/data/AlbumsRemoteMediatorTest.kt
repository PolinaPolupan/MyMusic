package com.example.mymusic.core.data

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.data.mediator.AlbumsRemoteMediator
import com.example.mymusic.core.database.MusicDatabase
import com.example.mymusic.core.database.model.LocalAlbumWithArtists
import com.example.mymusic.core.database.model.entities.LocalAlbum
import com.example.mymusic.core.designsystem.component.PreviewParameterData
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import com.example.mymusic.core.network.model.SavedAlbum
import com.example.mymusic.core.network.model.SavedAlbumsResponse
import com.example.mymusic.core.network.model.toLocal
import com.example.mymusic.core.network.model.toLocalAlbum
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@RunWith(AndroidJUnit4::class)
class AlbumsRemoteMediatorTest {

    private lateinit var mockAlbums: List<LocalAlbumWithArtists>

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var mockApi: FakeNetworkDataSource

    private val context: Context = ApplicationProvider.getApplicationContext()

    private val mockDb = Room.inMemoryDatabaseBuilder(
        context,
        MusicDatabase::class.java
    ).build()

    @Before
    fun setUp() {
        mockApi= FakeNetworkDataSource(
            dispatcher = testDispatcher,
            context = ApplicationProvider.getApplicationContext()
        )
    }


    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest(testDispatcher, timeout = 1000.seconds) {

        mockAlbums = mockApi.getSavedAlbums(0, 5)?.items?.map {
            LocalAlbumWithArtists(
                album = LocalAlbum(
                    id = it.album.id,
                    type = it.album.type,
                    imageUrl = it.album.images[0].url,
                    name = it.album.name
                ),
                simplifiedArtists = it.album.artists.toLocal()
            )
        } ?: emptyList()

        val remoteMediator = AlbumsRemoteMediator(
            networkDataSource = mockApi,
            musicDatabase = mockDb
        )

        val pagingState = PagingState<Int, LocalAlbumWithArtists>(
            listOf(),
            null,
            PagingConfig(5),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest(testDispatcher, timeout = 1000.seconds) {
        // To test endOfPaginationReached, don't set up the mockApi to return post
        // data here.
        val remoteMediator = AlbumsRemoteMediator(
            networkDataSource = mockApi,
            musicDatabase = mockDb
        )
        val pagingState = PagingState<Int, LocalAlbumWithArtists>(
            listOf(),
            null,
            PagingConfig(0),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }
}

