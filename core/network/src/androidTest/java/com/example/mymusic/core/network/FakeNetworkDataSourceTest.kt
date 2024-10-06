package com.example.mymusic.core.network

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymusic.core.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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