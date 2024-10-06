package com.example.mymusic.core.network

import androidx.test.core.app.ApplicationProvider
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class FakeNetworkDataSourceTest {

    private lateinit var subject: FakeNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = FakeNetworkDataSource(
            dispatcher = testDispatcher,
            context = ApplicationProvider.getApplicationContext()
        )
    }

    @Test
    fun test() {}

}